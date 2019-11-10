package com.mangobits.fitco.product

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator

import com.mangobits.fitco.R
import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import mangobits.startupkit.core.util.MessageAlertDialog

import java.util.ArrayList

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product)

        val productList = ArrayList<ProductCardExt>()

        val current = resources.configuration.locale

        val language = current.getLanguage()

        ProductService(this).listActiveExt(language, object : RestServiceListener<Array<ProductCardExt>> {
            override fun processCallback(resultado: JsonContainer, dados: Array<ProductCardExt>?) {

                if (resultado.success!!) {
                    if (dados != null) {

                        productList.addAll(dados.toCollection(ArrayList()))
                        val adapter = ProductAdapter(productList)

                        val recyclerView = findViewById<RecyclerView>(R.id.recviewProduct)

                        (recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false

                        recyclerView.setLayoutManager(LinearLayoutManager(this@ProductActivity))
                        recyclerView.setAdapter(adapter)
                        recyclerView.setHasFixedSize(true)

                    }
                } else {
                    val id = resources.getIdentifier(resultado.desc, "string", packageName)
                    val value = if (id == 0) resultado.desc else resources.getText(id) as String
                    val alert = MessageAlertDialog.createMsgDialog(this@ProductActivity, getText(R.string.validation).toString(), value!!).show()
                }
            }

        })


    }
}