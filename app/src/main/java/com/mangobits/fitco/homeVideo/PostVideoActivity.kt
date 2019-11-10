package com.mangobits.agroaz.postVideo

import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.facebook.places.model.PlaceFields.LINK
import com.google.android.gms.internal.mc

import com.mangobits.fitco.R


class PostVideoActivity : AppCompatActivity() {

    var idPost: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_video)

        idPost = intent.getSerializableExtra("idPost") as String


        val homeVideoView = findViewById<View>(R.id.homeVideoView) as FullScreenVideoView

        playVideo(homeVideoView)
        //teste2(mVideoView);
    }

    private fun playVideo(homeVideoView: FullScreenVideoView) {

        val urlSb = StringBuilder()
//        urlSb.append(PropertiesGeneralAndroid.getKey(this, "rest", "rest.ip"))
//        urlSb.append("/")
//        urlSb.append(PropertiesGeneralAndroid.getKey(this, "rest", "rest.project"))
        urlSb.append("http://192.168.123.10:8080/AgroAZWs/rs/post/video/c103a0ac-d137-41d0-b96f-a8401f35f6c7/video")

        val pDialog = ProgressDialog(this)
        //String pathVideo = "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4";
        val video_wait = applicationContext.resources.getString(R.string.video_wait)
        val video_inst = applicationContext.resources.getString(R.string.loading)


        // Set progressbar title
        pDialog.setTitle(video_inst)
        // Set progressbar message
        pDialog.setMessage(video_wait)
        pDialog.isIndeterminate = false
        pDialog.setCancelable(false)
        // Show progressbar
        pDialog.show()

        try {

            val path: String = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4"
//            val path: String = "http://192.168.123.10:8080/AgroAZWs/rs/post/video/1b01c28c-208d-4909-b3d1-842c1e7b9015/video"

            val video = Uri.parse(path)

//            PostService(this).video(homeVideoView, idPost!!, "video")
            homeVideoView.setVideoURI(video)
            val mc = MediaController(this)
            mc.setAnchorView(homeVideoView)
            mc.setMediaPlayer(homeVideoView)
            homeVideoView.setMediaController(mc)


        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        homeVideoView.setOnCompletionListener { finish() }

        homeVideoView.requestFocus()
        homeVideoView.setOnPreparedListener() // Close the progress bar and play the video
        {
            pDialog.dismiss()
            homeVideoView.start()
        }

    }
}