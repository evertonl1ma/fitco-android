package com.mangobits.fitco.app

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Messenger
import android.support.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.mangobits.startupkit.core.notification.Notification
import com.mangobits.startupkit.user.User
import mangobits.startupkit.core.util.LollipopBitmapMemoryCacheParamsSupplier
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.answers.Answers

class App : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
        Fabric.with(this, Answers())

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val imagePipelineConfig = ImagePipelineConfig
                .newBuilder(applicationContext)
                .setBitmapMemoryCacheParamsSupplier(LollipopBitmapMemoryCacheParamsSupplier(activityManager))
                .build()

        Fresco.initialize(applicationContext, imagePipelineConfig)

        instance = this

        applicationHandler = Handler(instance!!.mainLooper)

//        NativeLoader.initNativeLibs(App.instance)
    }

    fun getInstance(): App? {
        return instance
    }

    companion object {

        var instance: App? = null
            private set

        @Volatile
        var applicationHandler: Handler? = null

        var user: User? = null
        var lastNotification: Notification? = null
        var listNotifications: MutableList<Notification>? = null
        var messenger: Messenger? = null
        var gcmId: String? = null
        var idchat: String? = null
        var uriVideo: Uri? = null
        var frameVideo: Bitmap? = null
        var latitude: Double? = null
        var longitude: Double? = null

    }


}