package com.mangobits.fitco.product

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mangobits.fitco.R

class ProductAdapter(private val list: List<ProductCardExt>?) : RecyclerView.Adapter<ProductAdapter.RecViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product, parent, false)
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

        private val title: TextView
        private val origin: TextView
        private val desc: TextView
        private val subItem: View
        private val imgArrow: ImageView

        init {

            title = itemView.findViewById(R.id.item_title)
            origin = itemView.findViewById(R.id.sub_item_origin)
            desc = itemView.findViewById(R.id.sub_item_desc)
            subItem = itemView.findViewById(R.id.sub_item)
            imgArrow = itemView.findViewById(R.id.imgArrow)
        }

        fun bind(product: ProductCardExt) {
            val expanded = product.isExpanded

            subItem.visibility = if (expanded) View.VISIBLE else View.GONE

            if (expanded) imgArrow.setImageResource(R.drawable.arrow_up_green) else imgArrow.setImageResource(R.drawable.arrow_down_green)

            title.text = product.name
            origin.text = "Origin: " + product.origin
            desc.text = product.desc
        }
    }
}