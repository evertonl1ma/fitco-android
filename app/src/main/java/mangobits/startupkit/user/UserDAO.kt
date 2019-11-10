package com.mangobits.startupkit.user

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mangobits.startupkit.core.service.DatabaseHelper

/**
 * Created by angela on 26/02/18.
 */
class UserDAO(protected var context: Context) {

    @Throws(Exception::class)
    fun select(): UserDB? {

        var userDB: UserDB? = null
        var cursor: Cursor? = null
        val sqlLite = DatabaseHelper(context).writableDatabase

//        val colunas = arrayOf("id_user", "name", "password", "idFacebook")//, "type", "password", "idGoogle", "idFacebook")
        val colunas = arrayOf("id_user", "name", "type", "password", "idGoogle", "idFacebook")
//        cursor = sqlLite.query("user", colunas, null, null, null, null, null)

        cursor = sqlLite.query("user", colunas, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            userDB = UserDB()
            userDB.id = cursor.getString(cursor.getColumnIndex("id_user"))
            userDB.name = cursor.getString(cursor.getColumnIndex("name"))
            userDB.type = cursor.getString(cursor.getColumnIndex("type"))
            userDB.password = cursor.getString(cursor.getColumnIndex("password"))
            userDB.idFacebook = cursor.getString(cursor.getColumnIndex("idFacebook"))
            userDB.idGoogle = cursor.getString(cursor.getColumnIndex("idGoogle"))
        }

        if (cursor != null) {
            cursor.close()
        }

        return userDB
    }


    @Throws(Exception::class)
    fun insert(userDB: UserDB): Long {

        val sqlLite = DatabaseHelper(context).writableDatabase

        val content = ContentValues()

        content.put("id_user", userDB.id)
        content.put("name", userDB.name)
        content.put("type", userDB.type)
        content.put("password", userDB.password)
        content.put("idGoogle", userDB.idGoogle)
        content.put("idFacebook", userDB.idFacebook)

        return sqlLite.insert("user", null, content)
    }


    fun delete(id: String): Int {
        val sqlLite = DatabaseHelper(context).writableDatabase

        val where = "id_user = ? "

        val args = arrayOf(id)

        return sqlLite.delete("user", where, args)
    }
}