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

    fun bind(movieData: MovieDataRoom, onItemClickListener: ((MovieDataRoom) -> Unit)?) {
        title.text = movieData.movieTitle
        year.text = movieData.movieReleaseDate?.let { "".getYearFromStringFullDate(it) }
        rating.text = ratingFormat.format(movieData.movieRating)
        image.load("https://image.tmdb.org/t/p/w500${movieData.moviePoster}")
        itemView.setOnClickListener { onItemClickListener?.invoke(movieData) }
    }

}