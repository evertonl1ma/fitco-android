package com.mangobits.fitco.price

import android.os.Bundle
import android.support.v4.content.res.TypedArrayUtils.getText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import com.google.android.gms.internal.l

import com.mangobits.fitco.R
import com.mangobits.fitco.post.Post
import com.mangobits.fitco.product.*
import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import kotlinx.android.synthetic.main.prices.*
import kotlinx.android.synthetic.main.prices_detail.*
import mangobits.startupkit.core.util.MessageAlertDialog
import java.util.*
import kotlin.collections.ArrayList

class PriceProductDetailActivity : AppCompatActivity() {

    var productPriceCard: ProductPriceCard? = null
    var productPriceGroup: ProductPriceGroup? = null

    var listProductPriceCardDetail: ArrayList<ProductPriceCardDetail>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prices_detail)

        productPriceGroup = intent.getSerializableExtra("productPriceGroup") as? ProductPriceGroup
        productPriceCard = intent.getSerializableExtra("productPriceCard") as? ProductPriceCard

        txtNameProduct.text = productPriceGroup!!.nameProduct
        txtDesc.text = productPriceCard!!.desc


        val current = resources.configuration.locale

        val language = current.getLanguage()

        ProductService(this).productPriceGroup(language, productPriceGroup!!.idProduct!!, productPriceCard!!.originPrice!!, object : RestServiceListener<ProductPriceGroup> {
            override fun processCallback(resultado: JsonContainer, dados: ProductPriceGroup?) {

                if (resultado.success!!) {
                    if (dados != null) {


                        productPriceGroup = dados

                        listProductPriceCardDetail = productPriceGroup!!.priceCard!!.listProductPriceCardDetail!!.toCollection(ArrayList())


                        val adapter = PriceProductDetailAdapter(this@PriceProductDetailActivity, listProductPriceCardDetail)

                        val recyclerView = findViewById<RecyclerView>(R.id.recviewPriceDetail)

                        (recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false

                        recyclerView.setLayoutManager(LinearLayoutManager(this@PriceProductDetailActivity))
                        recyclerView.setAdapter(adapter)
                        recyclerView.setHasFixedSize(true)


                    }
                } else {
                    val id = resources.getIdentifier(resultado.desc, "string", packageName)
                    val value = if (id == 0) resultado.desc else resources.getText(id) as String
                    val alert = MessageAlertDialog.createMsgDialog(this@PriceProductDetailActivity, getText(R.string.validation).toString(), value!!).show()
                }
            }

        })


    }
}