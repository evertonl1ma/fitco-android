package com.mangobits.agroaz.splash

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log


import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.user.User
import com.mangobits.startupkit.user.UserDB
import com.mangobits.startupkit.user.UserFacade
import com.mangobits.startupkit.user.UserService
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.util.Base64
import android.view.View
import android.widget.VideoView

import com.mangobits.fitco.R
import com.mangobits.fitco.app.App
import com.mangobits.fitco.home.HomeActivity


class SplashActivity : AppCompatActivity() {

    var splashTime: Boolean = false

    var loadData: Boolean = false

    var connection: Boolean = false

    var splashDelay: Handler = Handler()

    var nextScreen: Class<*>? = null

    var videoSplash: VideoView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        try {


            videoSplash = findViewById<View>(R.id.splashVideo) as VideoView

            val video = Uri.parse("android.resource://" + packageName + "/" + R.raw.fitco)
            videoSplash!!.setVideoURI(video)
            //            videoSplash.setBackground(getResources().getDrawable(R.drawable.back_video));

            //            videoSplash.start();

            videoSplash!!.setOnErrorListener(MediaPlayer.OnErrorListener { mediaPlayer, i, i1 -> false })

            videoSplash!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { videoSplash!!.start() })


            videoSplash!!.setOnCompletionListener(MediaPlayer.OnCompletionListener { Log.d("yep", "") })

        } catch (ex: Exception) {
            ex.printStackTrace()
        }



        try {
            val info = packageManager.getPackageInfo(
                    "br.com.redejobee",
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }



        initialLoading()

    }


    fun initialLoading() {

        //start the splah minimal time
        splashDelay.postDelayed(delay, 3800)

        //load the user data
        var userDB: UserDB? = null
        try {
            userDB = UserFacade(applicationContext).retrieveFirst()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        //habemos usuario
        if (userDB != null) {

//            UserService(this).load(userDB.id!!, object : RestServiceListener<User> {
            UserService(this).autoLogin(userDB, object : RestServiceListener<User> {
                override fun processCallback(resultado: JsonContainer, user: User?) {

//todo:erro firebase     var keyAndroid =  FirebaseInstanceId.getInstance().token
//                    App.gcmId = keyAndroid

                    loadData = true
                    connection = true

                    if (resultado.success!!) {

                        App.user = user

                        nextScreen = HomeActivity::class.java
//
                        if (user!!.info!!["flagPreference"] == null) {
                            nextScreen = HomeActivity::class.java
                        }
//                        else if (user.info!!["zipCode"] == null) {
//
//                            nextScreen = RegisterAddressActivity::class.java
//                        }
//                        /*else if (user.info!!["idCompany"]  == null){
//
//                            nextScreen = RegisterCompanyAddressActivity::class.java
//
//                        }*/ else {
//                            nextScreen = HomePartnerActivity::class.java
//                        }
//
//                    } else if (user!!.type.equals("CUSTOMER")) {
//                        if (user.phone == null) {
//                            nextScreen = RegisterProfileActivity::class.java
//                        } else if (user.info!!["zipCode"] == null) {
//
//                            nextScreen = RegisterAddressActivity::class.java
////                        }else if (orderService.id == null) {
////
////                            nextScreen = CategoryActivity::class.java
//                        } else {
//                            nextScreen = HomeCustomerActivity::class.java
//                        }
                    } else {
                        nextScreen = HomeActivity::class.java

                    }

                    showNext()
                }
                // }
            })
        } else {

            connection = true
            loadData = true

            nextScreen = HomeActivity::class.java
            showNext()
        }
    }


    fun showNext() {

        if (splashTime && loadData) {

            if (connection) {

                finish()

                val intent = Intent(this@SplashActivity, nextScreen)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {

                val alertDialog = AlertDialog.Builder(this)

                alertDialog.setTitle(getText(R.string.alert).toString())

                alertDialog.setMessage(getText(R.string.no_connection).toString())

                alertDialog.setPositiveButton(getText(R.string.ok)) { _, _ -> initialLoading() }

                alertDialog.create()

                alertDialog.show()


            }
        }
    }


    val delay = Runnable {
        splashTime = true
        showNext()
    }

}