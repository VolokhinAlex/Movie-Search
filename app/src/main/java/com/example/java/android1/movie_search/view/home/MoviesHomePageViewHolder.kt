package com.example.java.android1.movie_search.view.home

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB

class MoviesHomePageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: AppCompatImageView
    private val title: TextView
    private val releaseDate: TextView
    private val ratingOfMovie: TextView

    init {
        image = itemView.findViewById(R.id.item_movie_card_image)
        title = itemView.findViewById(R.id.item_movie_card_title)
        releaseDate = itemView.findViewById(R.id.item_movie_card_date)
        ratingOfMovie = itemView.findViewById(R.id.item_movie_card_rating)
    }

    fun bind(movieData: MovieDataTMDB, onItemClickListener: ((MovieDataTMDB) -> Unit)?) {
        image.setImageResource(R.drawable.movie_image)
        title.text = movieData.title
        movieData.release_date?.apply {
            releaseDate.text = this.substring(0, this.indexOf("-"))
        }
        ratingOfMovie.text = movieData.vote_average.toString()
        itemView.setOnClickListener { onItemClickListener?.invoke(movieData) }
    }

}