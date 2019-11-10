package com.mangobits.fitco.price

import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mangobits.fitco.R
import com.mangobits.fitco.product.ProductPriceCard
import com.mangobits.fitco.product.ProductPriceGroup

class PriceAdapter(private val list: List<ProductPriceGroup>?) : RecyclerView.Adapter<PriceAdapter.RecViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_price, parent, false)
        return RecViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        val product = list!![position]

        holder.bind(product)

        holder.itemView.setOnClickListener { v ->
            val expanded = product.isExpanded
            product.isExpanded = !expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cv: ConstraintLayout
        private val title: TextView
        private val subItem: View
        private val imgArrow: ImageView
        private val recviewPriceCard: RecyclerView

        init {


            cv = itemView.findViewById(R.id.clPrice)
            title = itemView.findViewById(R.id.item_title)
            subItem = itemView.findViewById(R.id.sub_item)
            imgArrow = itemView.findViewById(R.id.imgArrow)
            recviewPriceCard = itemView.findViewById(R.id.recviewPriceCard)

        }

        fun bind(product: ProductPriceGroup) {
            val expanded = product.isExpanded

            subItem.visibility = if (expanded) View.VISIBLE else View.GONE

            if (expanded) imgArrow.setImageResource(R.drawable.arrow_up_green) else imgArrow.setImageResource(R.drawable.arrow_down_green)


            val listPriceCard = ArrayList<ProductPriceCard>()
            listPriceCard.addAll(product.listPriceCard!!.toCollection(ArrayList()))


            val adapterPriceCard = PriceCardAdapter(cv.context, listPriceCard,product)
//            adapterPriceCard.setClickProductPriceCardListener(this@PriceActivity)
            recviewPriceCard.setLayoutManager(LinearLayoutManager(cv.context))
            recviewPriceCard.setAdapter(adapterPriceCard)
            adapterPriceCard.setListItens(listPriceCard)
            adapterPriceCard.updateItems()



            title.text = product.nameProduct
//            origin.text = "Origin: " + product.origin
//            desc.text = product.desc

        }


    }


}