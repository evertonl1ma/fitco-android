package com.mangobits.fitco.product

import android.content.Context


import com.mangobits.startupkit.core.service.RestService
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.core.service.ServiceMethodEnum

class ProductService(context: Context) : RestService(context, "productExt") {


    fun listActiveExt(language: String, callback: RestServiceListener<Array<ProductCardExt>>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("listActiveExt/")
            urlServico.append(language)


            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Array<ProductCardExt>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun listPriceGroup(language: String, callback: RestServiceListener<Array<ProductPriceGroup>>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("listPriceGroup/")
            urlServico.append(language)


            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, Array<ProductPriceGroup>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun productPriceGroup(language: String, idProduct: String, originPrice: String, callback: RestServiceListener<ProductPriceGroup>) {

        try {

            showProgress = false

            val urlServico = StringBuilder()

            urlServico.append("productPriceGroup/")
            urlServico.append(language)
            urlServico.append("/")
            urlServico.append(idProduct)
            urlServico.append("/")
            urlServico.append(originPrice)


            execute(callback, urlServico.toString(), null, ServiceMethodEnum.GET, ProductPriceGroup::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}