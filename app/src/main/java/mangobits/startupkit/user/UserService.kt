package com.mangobits.startupkit.user

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import br.com.bestag.bestagandroid.util.PhotoUpload
import com.facebook.drawee.view.SimpleDraweeView
import com.mangobits.startupkit.core.service.RestService
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.core.service.RestServicePreProcessListener
import com.mangobits.startupkit.core.service.ServiceMethodEnum
import mangobits.startupkit.core.util.ImagemUtil

/**
 * Created by angela on 26/02/18.
 */
class UserService (context: Context) : RestService(context, "userExt") {

    fun loginFB(user: User, callback: RestServiceListener<*>) {

        showProgress = true

        execute(callback, "loginFB", user, ServiceMethodEnum.POST, User::class.java)
    }


    fun loginGoogle(user: User, callback: RestServiceListener<*>) {

        showProgress = true

        execute(callback, "loginGoogle", user, ServiceMethodEnum.POST, User::class.java)
    }


    fun login(user: User, callback: RestServiceListener<User>) {

        showProgress = true

        execute(callback, "login", user, ServiceMethodEnum.POST, User::class.java)
    }

    fun save(user: User, callback: RestServiceListener<String>) {

        showProgress = true

        execute(callback, "save", user, ServiceMethodEnum.POST, String::class.java)
    }



    fun autoLogin(user: UserDB, callback: RestServiceListener<User>) {

        execute(callback, "autoLogin", user, ServiceMethodEnum.POST, User::class.java)
    }


    fun newUser(user: User, callback: RestServiceListener<*>) {

        showProgress = true

        execute(callback, "newUser", user, ServiceMethodEnum.POST, User::class.java)
    }


    fun update(user: User, callback: RestServiceListener<String>) {

        showProgress = true

        execute(callback, "update", user, ServiceMethodEnum.POST, String::class.java)
    }

    fun updatePassword(user: User, callback: RestServiceListener<*>) {

        showProgress = true

        execute(callback, "updatePassword", user, ServiceMethodEnum.POST, String::class.java)
    }

    fun updateStartInfo(user: UserStartInfo, callback: RestServiceListener<String>) {

        showProgress = true

        execute(callback, "updateStartInfo", user, ServiceMethodEnum.POST, String::class.java)
    }
//
//
    fun showAvatar(networkImageView: SimpleDraweeView, idUser: String) {

        showProgress = true

        val urlRestImagem = StringBuilder()

        urlRestImagem.append(super.urlBase)

        urlRestImagem.append("showAvatar")

        urlRestImagem.append("/")

        urlRestImagem.append(idUser)

        urlRestImagem.append("/main")

        val uri = Uri.parse(urlRestImagem.toString())

        networkImageView.setImageURI(uri)
    }


    fun load(idUser: String, callback: RestServiceListener<*>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("load/")

            urlServico.append(idUser)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun loadByEvent(idEvent: String,email: String, callback: RestServiceListener<User>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("loadByEvent/")

            urlServico.append(idEvent)

            urlServico.append("/")

            urlServico.append(email)



            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun saveAvatar(bitmapFoto: Bitmap?, photoUpload: PhotoUpload, callback: RestServiceListener<String>) {

        showProgress = true

        preProcess = object : RestServicePreProcessListener {
            override fun process() {

                if (bitmapFoto != null) {

                    val base64 = ImagemUtil.converteImagemBase64(bitmapFoto)
                    photoUpload.photo = base64
                }
            }
        }

        execute(callback, "saveAvatar", photoUpload, ServiceMethodEnum.POST, String::class.java)
    }

    fun saveFacebookAvatar(photoUpload: PhotoUpload, callback: RestServiceListener<*>) {

        showProgress = true

        execute(callback, "saveFacebookAvatar", photoUpload, ServiceMethodEnum.POST, String::class.java)
    }


//
//    fun saveGallery(bitmapFoto: Bitmap?, photoUpload: PhotoUpload, callback: RestServiceListener<*>) {
//
//        showProgress = true
//
//        preProcess = object : RestServicePreProcessListener {
//            override fun process() {
//
//                if (bitmapFoto != null) {
//
//                    val base64 = ImagemUtil.converteImagemBase64(bitmapFoto)
//                    photoUpload.setPhoto(base64)
//                }
//            }
//        }
//
//        execute(callback, "saveGallery", photoUpload, ServiceMethodEnum.POST, PhotoUpload::class.java)
//    }
//
//    fun loadImageByIndex(networkImageView: SimpleDraweeView, id: String, index: Int?) {
//
//        showProgress = true
//
//        val urlRestImagem = StringBuilder()
//
//        urlRestImagem.append(super.urlBase)
//
//        urlRestImagem.append("loadImageByIndex")
//
//        urlRestImagem.append("/")
//
//        urlRestImagem.append(id)
//
//        urlRestImagem.append("/")
//
//        urlRestImagem.append(index)
//
//        val uri = Uri.parse(urlRestImagem.toString())
//
//        networkImageView.setImageURI(uri)
//    }
//
//
//    fun saveFacebookAvatar(photoUpload: PhotoUpload, callback: RestServiceListener<*>) {
//
//        showProgress = true
//
//        execute(callback, "saveFacebookAvatar", photoUpload, ServiceMethodEnum.POST, User::class.java)
//    }


    fun forgotPassword(email: String, callback: RestServiceListener<String>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("forgotPassword/")

            urlServico.append(email)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    fun imagemUrlVideo(networkImageView: SimpleDraweeView, url: String?) {
//
//        if (url != null) {
//
//            val uri = Uri.parse(url)
//
//            networkImageView.setImageURI(uri)
//        }
//    }
}