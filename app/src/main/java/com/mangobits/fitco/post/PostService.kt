package com.mangobits.fitco.post

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.preference.Preference
import br.com.bestag.bestagandroid.util.PhotoUpload
import com.facebook.drawee.view.SimpleDraweeView

import com.mangobits.agroaz.postVideo.FullScreenVideoView


import com.mangobits.startupkit.core.notification.Notification
import com.mangobits.startupkit.core.service.RestService
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.core.service.RestServicePreProcessListener
import com.mangobits.startupkit.core.service.ServiceMethodEnum
import com.mangobits.startupkit.user.InfoUrl
import mangobits.startupkit.core.util.ImagemUtil
import org.w3c.dom.Comment

class PostService(context: Context) : RestService(context, "post") {


    fun search(postSearch: PostSearch, callback: RestServiceListener<Array<Post>>) {

        try {

            showProgress = true

            execute(callback, "search", postSearch, ServiceMethodEnum.POST, Array<Post>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun image(networkImageView: SimpleDraweeView, postId: String, title: String) {

        showProgress = true

        val urlRestImagem = StringBuilder()

        urlRestImagem.append(super.urlBase)

        urlRestImagem.append("image")

        urlRestImagem.append("/")

        urlRestImagem.append(postId)

        urlRestImagem.append("/")

        urlRestImagem.append(title)

        val uri = Uri.parse(urlRestImagem.toString())

        networkImageView.setImageURI(uri)
    }

    fun video(videoView: FullScreenVideoView, postId: String, title: String) {

        showProgress = true

        val urlRestImagem = StringBuilder()

        urlRestImagem.append(super.urlBase)

        urlRestImagem.append("video")

        urlRestImagem.append("/")

        urlRestImagem.append(postId)

        urlRestImagem.append("/")

        urlRestImagem.append(title)

        val uri = Uri.parse(urlRestImagem.toString())

        videoView.setVideoURI(uri)
    }

//    fun like(like: Like, callback: RestServiceListener<Like>) {
//
//        try {
//
//            showProgress = true
//
//            execute(callback, "like", like, ServiceMethodEnum.POST, Like::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun save(post: Post, callback: RestServiceListener<Post>) {

        try {

            showProgress = true

            execute(callback, "save", post, ServiceMethodEnum.POST, Post::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveImage(bitmapFoto: Bitmap?, photoUpload: PhotoUpload, callback: RestServiceListener<String>) {

        showProgress = true

        preProcess = object : RestServicePreProcessListener {
            override fun process() {

                if (bitmapFoto != null) {

                    val base64 = ImagemUtil.converteImagemBase64(bitmapFoto)
                    photoUpload.photo = base64
                }
            }
        }

        execute(callback, "saveImage", photoUpload, ServiceMethodEnum.POST, String::class.java)
    }


    fun saveVideo(photoUpload: PhotoUpload, callback: RestServiceListener<String>) {

        showProgress = true

        execute(callback, "saveVideo", photoUpload, ServiceMethodEnum.POST, String::class.java)
    }

    fun favorite(idPost: String, callback: RestServiceListener<String>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("favorite/")

            urlServico.append(idPost)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun load(idPost: String, callback: RestServiceListener<Post>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("load/")

            urlServico.append(idPost)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Post::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun changeStatus(idPost: String, callback: RestServiceListener<String>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("changeStatus/")

            urlServico.append(idPost)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun verifyUrl(encodeUrl: String, callback: RestServiceListener<InfoUrl>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("verifyUrl/")

            urlServico.append(encodeUrl)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, InfoUrl::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    fun listFavorites(postSearch: PostSearch, callback: RestServiceListener<Array<Post>>) {
//
//        try {
//
//            showProgress = true
//
//            execute(callback, "listFavorites", postSearch, ServiceMethodEnum.POST, Array<Post>::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun listFavorites(idUser: String, callback: RestServiceListener<Array<Post>>) {

        try {

            showProgress = false

            val urlService = StringBuilder()

            urlService.append("listFavorites/")

            urlService.append(idUser)

            execute(callback, urlService.toString(), null, ServiceMethodEnum.GET, Array<Post>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeComment(comment: Comment, callback: RestServiceListener<String>) {

        try {

            showProgress = true

            execute(callback, "removeComment", comment, ServiceMethodEnum.POST, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listAllCommentsByPost(idPost: String, callback: RestServiceListener<Array<Comment>>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("listAllCommentsByPost/")

            urlServico.append(idPost)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Array<Comment>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun addComment(comment: Comment, callback: RestServiceListener<String>) {

        try {

            showProgress = true

            execute(callback, "addComment", comment, ServiceMethodEnum.POST, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}