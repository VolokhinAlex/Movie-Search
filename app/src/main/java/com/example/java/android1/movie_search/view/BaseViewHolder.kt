package com.example.java.android1.movie_search.view

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import java.text.DecimalFormat

class BaseMovieCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: AppCompatImageView
    private val title: TextView
    private val rating: TextView
    private val year: TextView
    private val ratingFormat = DecimalFormat("#.#")

    init {
        image = itemView.findViewById(R.id.movie_card_item_image)
        title = itemView.findViewById(R.id.movie_card_item_title)
        rating = itemView.findViewById(R.id.movie_card_item_rating)
        year = itemView.findViewById(R.id.movie_card_item_year)
    }

    fun bind(movieData: MovieDataTMDB, onItemClickListener: ((MovieDataTMDB) -> Unit)?) {
        title.text = movieData.title
        year.text = movieData.release_date?.let { "".getYearFromStringFullDate(it) }
        rating.text = ratingFormat.format(movieData.vote_average)
        image.load("https://image.tmdb.org/t/p/w500${movieData.poster_path}")
        itemView.setOnClickListener { onItemClickListener?.invoke(movieData) }
    }

}