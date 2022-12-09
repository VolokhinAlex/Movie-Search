package com.example.java.android1.movie_search.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieData

class MoviesHomePageAdapter(
    private var onItemClickListener: ((MovieData) -> Unit)?
) : RecyclerView.Adapter<MoviesHomePageViewHolder>() {

    private var movieData: List<MovieData> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieData(data: List<MovieData>) {
        movieData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHomePageViewHolder {
        return MoviesHomePageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesHomePageViewHolder, position: Int) {
        holder.bind(movieData = movieData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = movieData.size

}