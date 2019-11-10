package com.mangobits.fitco.price

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.widget.EditText
import android.widget.TextView

import com.mangobits.fitco.R
import com.mangobits.fitco.product.*
import com.mangobits.fitco.utils.Currency
import com.mangobits.fitco.utils.CurrencyRequest2
import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import kotlinx.android.synthetic.main.prices.*
import mangobits.startupkit.core.util.CEPUtils
import mangobits.startupkit.core.util.MessageAlertDialog
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class PriceActivity : AppCompatActivity(), CurrencyRequest2.CurrencyRequest2Interface {


    var cepUtils: CEPUtils? = null


    val productList = ArrayList<ProductPriceGroup>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prices)



        getCurrency()


        val current = resources.configuration.locale

        val language = current.getLanguage()


//        ProductService(this).currency(object : RestServiceListener<USD_BRL> {
//            override fun processCallback(resultado: JsonContainer, dados: USD_BRL?) {
//
//                if (resultado.success!!) {
//                    if (dados != null) {
//
//                        val currency = dados
//                    }
//                } else {
//                    val id = resources.getIdentifier(resultado.desc, "string", packageName)
//                    val value = if (id == 0) resultado.desc else resources.getText(id) as String
//                    val alert = MessageAlertDialog.createMsgDialog(this@PriceActivity, getText(R.string.validation).toString(), value!!).show()
//                }
//            }
//
//        })


        ProductService(this).listPriceGroup(language,
                object : RestServiceListener<Array<ProductPriceGroup>> {
                    override fun processCallback(resultado: JsonContainer, dados: Array<ProductPriceGroup>?) {

                        if (resultado.success!!) {
                            if (dados != null) {


                                productList.addAll(dados.toCollection(ArrayList()))


                                val adapter = PriceAdapter(productList)

                                val recyclerView = findViewById<RecyclerView>(R.id.recviewPrice)

                                (recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false

                                recyclerView.setLayoutManager(LinearLayoutManager(this@PriceActivity))
                                recyclerView.setAdapter(adapter)
                                recyclerView.setHasFixedSize(true)


                            }
                        } else {
                            val id = resources.getIdentifier(resultado.desc, "string", packageName)
                            val value = if (id == 0) resultado.desc else resources.getText(id) as String
                            val alert = MessageAlertDialog.createMsgDialog(this@PriceActivity, getText(R.string.validation).toString(), value!!).show()
                        }
                    }

                })


    }


    private fun getCurrency() {
        CurrencyRequest2(this@PriceActivity).execute()
    }

    override fun getUriRequestDollar(): String {
        return "https://free.currencyconverterapi.com/api/v6/convert?apiKey=c22a11980b9062ce7d27&q=USD_BRL&compact=y"

    }

    override fun getUriRequestEuro(): String {
        return "https://free.currencyconverterapi.com/api/v6/convert?apiKey=c22a11980b9062ce7d27&q=EUR_BRL&compact=y"

    }

    override fun lockFields(boo: Boolean) {
//        cepUtils!!.lockFields(boo)
    }


    override fun setAddressFields(address: Currency?) {
        val current = resources.configuration.locale

        val language = "pt"

        val country = "br"

        val format = NumberFormat.getCurrencyInstance(Locale(language, country))
        txtValueDollar.text = format.format(address!!.valueDollar)
        txtValueEuro.text = format.format(address!!.valueEuro)

    }


}