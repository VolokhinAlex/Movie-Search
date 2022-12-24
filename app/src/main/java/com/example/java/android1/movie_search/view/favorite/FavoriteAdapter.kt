package com.example.java.android1.movie_search.view.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataRoom

class FavoriteAdapter(
    private var onItemClickListener: ((MovieDataRoom) -> Unit)?
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var movieData: List<MovieDataRoom> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieData(data: List<MovieDataRoom>) {
        movieData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.base_movie_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movieData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = movieData.size

}