package com.example.java.android1.movie_search.view.without_compose.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataRoom

class FavoriteAdapter(
    private var onItemClickListener: ((MovieDataRoom) -> Unit)?
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var listFavoriteMoviesData: List<MovieDataRoom> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteMovieListData(listMovies: List<MovieDataRoom>) {
        listFavoriteMoviesData = listMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.base_movie_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavoriteMoviesData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = listFavoriteMoviesData.size

}