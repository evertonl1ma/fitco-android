package com.mangobits.fitco.product

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mangobits.fitco.R

class RecAdapter(private val list: List<Movie>?) : RecyclerView.Adapter<RecAdapter.RecViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return RecViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        val movie = list!![position]

        holder.bind(movie)

        holder.itemView.setOnClickListener { v ->
            val expanded = movie.isExpanded
            movie.isExpanded = !expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView
        private val genre: TextView
        private val year: TextView
        private val subItem: View

        init {

            title = itemView.findViewById(R.id.item_title)
            genre = itemView.findViewById(R.id.sub_item_genre)
            year = itemView.findViewById(R.id.sub_item_year)
            subItem = itemView.findViewById(R.id.sub_item)
        }

        fun bind(movie: Movie) {
            val expanded = movie.isExpanded

            subItem.visibility = if (expanded) View.VISIBLE else View.GONE

            title.text = movie.title
            genre.text = "Genre: " + movie.genre
            year.text = "Year: " + movie.year
        }
    }
}