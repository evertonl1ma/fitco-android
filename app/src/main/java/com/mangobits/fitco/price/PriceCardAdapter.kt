package com.mangobits.fitco.price

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.mangobits.fitco.R
import com.mangobits.fitco.R.id.lblCreationDate
import com.mangobits.fitco.app.App
import com.mangobits.fitco.product.ProductPriceCard
import com.mangobits.fitco.product.ProductPriceGroup


import mangobits.startupkit.core.util.TextUtils
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class PriceCardAdapter(private val context: Context, private var listItens: ArrayList<ProductPriceCard>?, private var productPriceGroup: ProductPriceGroup) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    private var clickProductPriceCardListener: ProductPriceCardHolder.ClickproductPriceCardListener? = null


    private var tamanhoAntigoLista = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = listItens!![position]


        (holder as? PriceCardAdapter.ProductPriceCardHolder)?.bindView(item, productPriceGroup)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var ph: RecyclerView.ViewHolder? = null


        val v = LayoutInflater.from(context).inflate(R.layout.item_price_card, parent, false)
        ph = ProductPriceCardHolder(v)
//        configClicks(v, ph)

        return ph
    }


//    private fun configClicks(view: View, holder: ProductPriceCardHolder) {
//
//
//        holder.cv.setOnClickListener { v -> clickProductPriceCardListener!!.clickProductPriceCard(v, holder.adapterPosition) }
//    }


    override fun getItemViewType(position: Int): Int {


        return 1
    }


    override fun getItemCount(): Int {

        return listItens!!.size
    }


    class ProductPriceCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cv: ConstraintLayout
        internal var dateCurrent: TextView
        internal var priceCurrent: TextView
        internal var pricePrevious: TextView
        internal var datePrevious: TextView
        internal var desc: TextView

        var productPriceCard: ProductPriceCard? = null
        var productPriceGroup: ProductPriceGroup? = null


        init {
            cv = itemView.findViewById(R.id.clPriceCard)
            dateCurrent = itemView.findViewById(R.id.txtDatePrevious)
            priceCurrent = itemView.findViewById(R.id.txtPricePrevious)
            pricePrevious = itemView.findViewById(R.id.txtPriceCurrent)
            datePrevious = itemView.findViewById(R.id.txtDateCurrent)
            desc = itemView.findViewById(R.id.item_title)

        }


        fun bindView(productPriceCard: ProductPriceCard, productPriceGroup: ProductPriceGroup) {

            this.productPriceCard = productPriceCard
            this.productPriceGroup = productPriceGroup


            val formatter = SimpleDateFormat("MMM/yyyy")


            val current = cv.resources.configuration.locale

            val language = "en"

            val country = "us"

            val format = NumberFormat.getCurrencyInstance(Locale(language, country))


            priceCurrent.text = format.format(productPriceCard.priceCurrent)
            pricePrevious.text = format.format(productPriceCard.pricePrevious)





            if (productPriceCard.dateCurrent != null) {
                dateCurrent.text = formatter.format(productPriceCard.dateCurrent)

            }



            if (productPriceCard.datePrevious != null) {
                datePrevious.text = formatter.format(productPriceCard.datePrevious)

            }



            desc.text = productPriceCard.desc

            cv.setOnClickListener(clickDetail)


        }

        var clickDetail = View.OnClickListener {


            val intent = Intent(cv.context, PriceProductDetailActivity::class.java)
            intent.putExtra("productPriceGroup", productPriceGroup)
            intent.putExtra("productPriceCard", productPriceCard)
            cv.context.startActivity(intent)

        }


    }


    fun setListItens(listItens: ArrayList<ProductPriceCard>) {

        if (this.listItens != null) {
            tamanhoAntigoLista = this.listItens!!.size
        }

        this.listItens = listItens
    }


    fun animateTo(models: List<ProductPriceCard>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedItems(models)
    }


    fun removeItem(position: Int): ProductPriceCard {
        val model = listItens!!.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: ProductPriceCard) {
        listItens!!.add(position, model)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = listItens!!.removeAt(fromPosition)
        listItens!!.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }


    private fun applyAndAnimateRemovals(newModels: List<ProductPriceCard>) {
        for (i in listItens!!.indices.reversed()) {
            val model = listItens!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }


    private fun applyAndAnimateAdditions(newModels: List<ProductPriceCard>) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            if (!listItens!!.contains(model)) {
                addItem(i, model)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<ProductPriceCard>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = listItens!!.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }


    fun updateItems() {

        notifyItemRangeInserted(tamanhoAntigoLista, listItens!!.size)
    }


//    interface ClickproductPriceCardListener {
//
//        fun clickProductPriceCard(view: View, position: Int)
//
//    }
//
//    fun setClickProductPriceCardListener(clickProductPriceCardListener: ClickproductPriceCardListener) {
//
//        this.clickProductPriceCardListener = clickProductPriceCardListener
//    }

}
