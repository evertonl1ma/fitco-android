package com.mangobits.startupkit.user

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mangobits.startupkit.core.service.DatabaseHelper

class EventDAO (protected var context: Context) {

    @Throws(Exception::class)
    fun select(): EventDB? {

        var eventDB: EventDB? = null
        var cursor: Cursor? = null
        val sqlLite = DatabaseHelper(context).writableDatabase

        val colunas = arrayOf("id_event")

        cursor = sqlLite.query("event", colunas, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            eventDB = EventDB()
            eventDB.id = cursor.getString(cursor.getColumnIndex("id_event"))
        }

        if (cursor != null) {
            cursor.close()
        }

        return eventDB
    }


    @Throws(Exception::class)
    fun selectAll(): ArrayList<EventDB>? {

        var list = ArrayList<EventDB>()
        var cursor: Cursor? = null
        val sqlLite = DatabaseHelper(context).writableDatabase

        val colunas = arrayOf("id_event")

        cursor = sqlLite.query("event", colunas, null, null, null, null, null)

        if (cursor != null) {

            while(cursor.moveToNext()){
                var eventDB = EventDB()
                eventDB.id = cursor.getString(cursor.getColumnIndex("id_event"))
                list.add(eventDB)
            }
        }

        if (cursor != null) {
            cursor.close()
        }

        return list
    }


    @Throws(Exception::class)
    fun insert(eventDB: EventDB): Long {

        val sqlLite = DatabaseHelper(context).writableDatabase

        val content = ContentValues()

        content.put("id_event", eventDB.id)

        return sqlLite.insert("event", null, content)
    }


    fun delete(id: String): Int {
        val sqlLite = DatabaseHelper(context).writableDatabase

        val where = "id_event = ? "

        val args = arrayOf(id)

        return sqlLite.delete("event", where, args)
    }
}