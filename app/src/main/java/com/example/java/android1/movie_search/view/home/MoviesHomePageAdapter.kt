package com.example.java.android1.movie_search.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieData

class MoviesHomePageAdapter(
    private val movieData: List<MovieData>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MoviesHomePageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHomePageViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card_item, parent, false)
        return MoviesHomePageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesHomePageViewHolder, position: Int) {
        holder.bind(movieData = movieData[position], onItemClickListener)
    }

    override fun getItemCount(): Int = movieData.size

}

interface OnItemClickListener {
    fun onItemClickListener(movieData: MovieData)
}