package com.mangobits.startupkit.user

import android.content.Context


class EventFacade (private val context: Context) {


//    @Throws(Exception::class)
//    fun save(event: Event) {
//
//        val eventDB = EventDB()
//        eventDB.id = event.id
//
//
//        val dao = EventDAO(context)
//        dao.insert(eventDB)
//    }


    @Throws(Exception::class)
    fun delete(idEvent: String) {

        val dao = EventDAO(context)
        dao.delete(idEvent)
    }


    fun retrieveFirst(): EventDB? {

        var eventDB: EventDB? = null

        try {
            val dao = EventDAO(context)

            eventDB = dao.select()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return eventDB
    }

    fun retrieveAll(): ArrayList<EventDB>? {

        var list: ArrayList<EventDB>? = null

        try {
            val dao = EventDAO(context)

            list = dao.selectAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }
}