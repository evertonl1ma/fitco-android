package com.mangobits.startupkit.core.notification

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import com.mangobits.fitco.app.App

import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.user.User

import java.util.ArrayList
import java.util.Arrays
import java.util.Calendar



/**
 * Created by diegobellimondego on 09/06/15.
 */
class NotificacaoIntentService : IntentService("NotificationService") {


    override fun onHandleIntent(intent: Intent?) {



        var user = App.user
        var notification = App.lastNotification
        //User user = (User) PurpleBag.getApplicationBag().getAttribute("user");
        //Notification notification = (Notification) PurpleBag.getApplicationBag().getAttribute("lastNotification");


        if (user != null && notification != null) {

            NotificationService(this).listNewNotifications(user.id!!, notification.id!!, object : RestServiceListener<Array<Notification>> {

                override fun processCallback(resultado: JsonContainer, dados: Array<Notification>?) {

                    processNotifications(dados, intent)
                }
            })
        } else if (user != null) {

            NotificationService(this).listNotifications(user.id!!, object : RestServiceListener<Array<Notification>> {
                override fun processCallback(resultado: JsonContainer, dados: Array<Notification>?) {

                    processNotifications(dados, intent)
                }
            })
        }
    }


    private fun processNotifications(data: Array<Notification>?, intent: Intent?) {

        try {

            if (data != null && data.size > 0) {

                var list: MutableList<Notification>? = App.listNotifications

                if (list == null) {
                    list = ArrayList()
                    App.listNotifications = list
//                    PurpleBag.getApplicationBag().setAttribute("notifications", list)
                }

                list.addAll(0, Arrays.asList(*data))

                App.lastNotification = list[0]
//                PurpleBag.getApplicationBag().setAttribute("lastNotification", list[0])
            }

            loadAlarm()

            val bundle = intent!!.extras
            if (bundle != null) {

//                if (PurpleBag.getApplicationBag().getAttribute("mainMenu") != null && PurpleBag.getApplicationBag().getAttribute("mainMenu")) {

//                    val messenger = PurpleBag.getApplicationBag().getAttribute("messenger") as Messenger
                val messenger = App.messenger

                val msg = Message.obtain()

                    try {
                        messenger!!.send(msg)
                    } catch (e: RemoteException) {
                        Log.i("error", "error")
                    }

//                }
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }


    private fun loadAlarm() {

        val cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, 5)

        val newIntent = Intent(this@NotificacaoIntentService, NotificacaoIntentService::class.java)
        val pintent = PendingIntent.getService(this@NotificacaoIntentService, 0, newIntent, 0)
        val alarm = this@NotificacaoIntentService.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.set(AlarmManager.RTC, cal.timeInMillis, pintent)
    }
}
