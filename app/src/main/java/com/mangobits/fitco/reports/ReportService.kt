package com.mangobits.fitco.reports

import android.content.Context
import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView

import com.mangobits.startupkit.core.service.RestService
import com.mangobits.startupkit.core.service.RestServiceListener

import com.mangobits.startupkit.core.service.ServiceMethodEnum


class ReportService(context: Context) : RestService(context, "report") {


    fun load(idPost: String, callback: RestServiceListener<Report>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("load/")

            urlServico.append(idPost)

            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Report::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun image(networkImageView: SimpleDraweeView, idReport: String) {

        showProgress = true

        val urlRestImagem = StringBuilder()

        urlRestImagem.append(super.urlBase)

        urlRestImagem.append("image")

        urlRestImagem.append("/")

        urlRestImagem.append(idReport)

        urlRestImagem.append("/")

        val uri = Uri.parse(urlRestImagem.toString())

        networkImageView.setImageURI(uri)
    }


    fun listAll(callback: RestServiceListener<Array<Report>>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("listAll/")


            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Array<Report>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun listReports(page: String, callback: RestServiceListener<Array<Report>>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("listReports/")
            urlServico.append(page)


            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Array<Report>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
