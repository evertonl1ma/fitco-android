package com.mangobits.startupkit.core.notification

import android.content.Context
import com.mangobits.startupkit.core.service.RestService
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.core.service.ServiceMethodEnum

class NotificationService (context: Context) : RestService(context, "notification") {

    fun listNotifications(idUser: String, callback: RestServiceListener<Array<Notification>>) {

        try {

            showProgress = false

            val urlService = StringBuilder()

            urlService.append("listNotifications/")

            urlService.append(idUser)

            execute(callback, urlService.toString(), null, ServiceMethodEnum.GET, Array<Notification>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeNotification(idNotification: String) {

        try {

            showProgress = false

            val urlService = StringBuilder()

            urlService.append("removeNotification/")

            urlService.append(idNotification)

            execute(null, urlService.toString(), null, ServiceMethodEnum.GET, Array<Notification>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




    fun listNewNotifications(idUser: String, lastNotification: String, callback: RestServiceListener<*>) {

        try {

            showProgress = false

            val urlService = StringBuilder()

            urlService.append("listNewNotifications/")

            urlService.append(idUser)

            urlService.append("/")

            urlService.append(lastNotification)

            execute(callback, urlService.toString(), null, ServiceMethodEnum.GET, Array<Notification>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun checkNotificationsAsReaded(idUser: String, callback: RestServiceListener<*>) {

        showProgress = false

        val urlService = StringBuilder()

        urlService.append("checkNotificationsAsReaded/")

        urlService.append(idUser)

        execute(callback, urlService.toString(), null, ServiceMethodEnum.GET)
    }

    fun checkNotificationAsReaded(idUser: String, idNotification: String, callback: RestServiceListener<*>) {

        showProgress = false

        val urlService = StringBuilder()

        urlService.append("checkNotificationAsReaded/")

        urlService.append(idUser)

        urlService.append("/")

        urlService.append(idNotification)

        execute(callback, urlService.toString(), null, ServiceMethodEnum.GET)
    }


    fun cleanNotifications(idUser: String, callback: RestServiceListener<*>) {

        showProgress = false

        val urlService = StringBuilder()

        urlService.append("cleanNotifications/")

        urlService.append(idUser)

        execute(callback, urlService.toString(), null, ServiceMethodEnum.GET)
    }


}