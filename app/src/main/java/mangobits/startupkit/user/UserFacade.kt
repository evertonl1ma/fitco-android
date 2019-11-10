package com.mangobits.startupkit.user

import android.content.Context
import com.mangobits.fitco.app.App

/**
 * Created by angela on 26/02/18.
 */
class UserFacade(private val context: Context) {


    @Throws(Exception::class)
    fun save(user: User) {

        val userDB = UserDB()
        userDB.id = user.id
        userDB.name = user.name
        userDB.type = user.type
        userDB.password = user.password
        userDB.idFacebook = user.idFacebook
        userDB.idGoogle = user.idGoogle

        val dao = UserDAO(context)
        dao.insert(userDB)
    }


    @Throws(Exception::class)
    fun delete(idUser: String) {

        val dao = UserDAO(context)
        dao.delete(idUser)

        App.user = null

//        PurpleBag.getApplicationBag().setAttribute("user", null)
    }


    fun retrieveFirst(): UserDB? {

        var userDB: UserDB? = null

        try {
            val dao = UserDAO(context)

            userDB = dao.select()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userDB
    }
}