package com.mangobits.startupkit.core.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by angela on 26/02/18.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DICTIONARY_TABLE_CREATE)
        db.execSQL(DICTIONARY_TABLE_EVENT_CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        private val DATABASE_VERSION = 3

        private val DATABASE_NAME = "agroaz"

        private val DICTIONARY_TABLE_CREATE = "CREATE TABLE user (" +
                " id_user TEXT, " +
                " name TEXT," +
                " password TEXT, " +
                " idGoogle TEXT, " +
                " idFacebook TEXT, " +
                " type TEXT);"

        private val DICTIONARY_TABLE_EVENT_CREATE = "CREATE TABLE event (" +
                " id_event TEXT);"

    }
}