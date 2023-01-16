package com.example.java.android1.movie_search.view.without_compose

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.convertStringFullDateToOnlyYear
import java.text.DecimalFormat

class BaseMovieCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    fun bind(movieData: MovieDataTMDB, onItemClickListener: ((MovieDataTMDB) -> Unit)?) {
        cardTitle.text = movieData.title
        cardReleaseDate.text = movieData.release_date?.let { "".convertStringFullDateToOnlyYear(it) }
        cardRating.text = ratingFormat.format(movieData.vote_average)
        cardImage.load("https://image.tmdb.org/t/p/w500${movieData.poster_path}")
        itemView.setOnClickListener { onItemClickListener?.invoke(movieData) }
    }

}