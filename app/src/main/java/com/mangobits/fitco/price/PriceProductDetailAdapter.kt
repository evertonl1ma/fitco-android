package com.mangobits.fitco.price

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.mangobits.fitco.R
import com.mangobits.fitco.R.id.lblCreationDate
import com.mangobits.fitco.product.ProductPriceCardDetail


import mangobits.startupkit.core.util.TextUtils
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class PriceProductDetailAdapter(private val context: Context, private var listItens: ArrayList<ProductPriceCardDetail>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    private var clickProductPriceCardDetailListener: ClickproductPriceCardDetailListener? = null

    private var tamanhoAntigoLista = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = listItens!![position]

        (holder as? PriceProductDetailAdapter.ProductPriceCardDetailHolder)?.bindView(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var ph: RecyclerView.ViewHolder? = null


        val v = LayoutInflater.from(context).inflate(R.layout.card_price_detail, parent, false)
        ph = ProductPriceCardDetailHolder(v)
        configClicks(v, ph)

        return ph
    }


    private fun configClicks(view: View, holder: ProductPriceCardDetailHolder) {


//        holder.cv.setOnClickListener { v -> clickProductPriceCardDetailListener!!.clickProductPriceCardDetail(v, holder.adapterPosition) }
    }


    override fun getItemViewType(position: Int): Int {


        return 1
    }


    override fun getItemCount(): Int {

        return listItens!!.size
    }


    class ProductPriceCardDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cv: ConstraintLayout
        internal var date: TextView
        internal var averagePrice: TextView


        var productPriceCardDetail: ProductPriceCardDetail? = null


        init {
            cv = itemView.findViewById(R.id.clPriceCard)
            date = itemView.findViewById(R.id.txtDate)
            averagePrice = itemView.findViewById(R.id.txtAveragePrice)


        }


        fun bindView(productPriceCardDetail: ProductPriceCardDetail) {

            this.productPriceCardDetail = productPriceCardDetail


            val formatter = SimpleDateFormat("dd/MM/yyyy")

            if (productPriceCardDetail.informedDate != null) {
                date.text = formatter.format(productPriceCardDetail.informedDate)

            }


            val current = cv.resources.configuration.locale

            val language = "en"

            val country = "us"

            val format = NumberFormat.getCurrencyInstance(Locale(language, country))
            averagePrice.text = format.format(productPriceCardDetail.price)


        }


    }


    fun setListItens(listItens: ArrayList<ProductPriceCardDetail>) {

        if (this.listItens != null) {
            tamanhoAntigoLista = this.listItens!!.size
        }

        this.listItens = listItens
    }


    fun animateTo(models: List<ProductPriceCardDetail>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedItems(models)
    }


    fun removeItem(position: Int): ProductPriceCardDetail {
        val model = listItens!!.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: ProductPriceCardDetail) {
        listItens!!.add(position, model)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = listItens!!.removeAt(fromPosition)
        listItens!!.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }


    private fun applyAndAnimateRemovals(newModels: List<ProductPriceCardDetail>) {
        for (i in listItens!!.indices.reversed()) {
            val model = listItens!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }


    private fun applyAndAnimateAdditions(newModels: List<ProductPriceCardDetail>) {
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

    private fun applyAndAnimateMovedItems(newModels: List<ProductPriceCardDetail>) {
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


//    interface ClickproductPriceCardDetailListener {
//
//        fun clickProductPriceCardDetail(view: View, position: Int)
//
//    }
//
//    fun setClickProductPriceCardDetailListener(clickProductPriceCardDetailListener: ClickproductPriceCardDetailListener) {
//
//        this.clickProductPriceCardDetailListener = clickProductPriceCardDetailListener
//    }

}
