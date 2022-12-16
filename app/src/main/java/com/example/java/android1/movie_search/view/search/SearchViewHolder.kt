package com.example.java.android1.movie_search.view.search

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import java.text.DecimalFormat

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: AppCompatImageView
    private val title: TextView
    private val rating: TextView
    private val year: TextView
    private val ratingFormat = DecimalFormat("#.#")

    init {
        image = itemView.findViewById(R.id.search_card_item_image)
        title = itemView.findViewById(R.id.search_card_item_title)
        rating = itemView.findViewById(R.id.search_card_item_rating)
        year = itemView.findViewById(R.id.search_card_item_year)
    }

    fun bind(movieData: MovieDataTMDB, listener: ((MovieDataTMDB) -> Unit)?) {
        title.text = movieData.title
        year.text = movieData.release_date
        rating.text = ratingFormat.format(movieData.vote_average)
        itemView.setOnClickListener {
            listener?.invoke(movieData)
        }
    }

}