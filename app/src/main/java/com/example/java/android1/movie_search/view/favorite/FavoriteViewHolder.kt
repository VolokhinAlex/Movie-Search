package com.example.java.android1.movie_search.view.favorite

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import java.text.DecimalFormat

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardImage: AppCompatImageView
    private val cardTitle: TextView
    private val cardRating: TextView
    private val cardReleaseDate: TextView
    private val ratingFormat = DecimalFormat("#.#")

    init {
        cardImage = itemView.findViewById(R.id.movie_card_item_image)
        cardTitle = itemView.findViewById(R.id.movie_card_item_title)
        cardRating = itemView.findViewById(R.id.movie_card_item_rating)
        cardReleaseDate = itemView.findViewById(R.id.movie_card_item_year)
    }

    fun bind(movieData: MovieDataRoom, onItemClickListener: ((MovieDataRoom) -> Unit)?) {
        cardTitle.text = movieData.movieTitle
        cardReleaseDate.text = movieData.movieReleaseDate?.let { "".getYearFromStringFullDate(it) }
        cardRating.text = ratingFormat.format(movieData.movieRating)
        cardImage.load("https://image.tmdb.org/t/p/w500${movieData.moviePoster}")
        itemView.setOnClickListener { onItemClickListener?.invoke(movieData) }
    }

}