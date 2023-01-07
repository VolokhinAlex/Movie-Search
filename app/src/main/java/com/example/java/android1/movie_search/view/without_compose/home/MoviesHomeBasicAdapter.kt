package com.example.java.android1.movie_search.view.without_compose.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieChildListData

class MoviesHomeBasicAdapter(
    private val onCategoryClickListener: (String) -> Unit
) : RecyclerView.Adapter<MovieHomeBasicViewHolder>() {

    private var listCategoryMovies: List<MovieChildListData> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setParentListData(listMoviesCategory: List<MovieChildListData>) {
        this.listCategoryMovies = listMoviesCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHomeBasicViewHolder {
        return MovieHomeBasicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_home_basic_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHomeBasicViewHolder, position: Int) {
        holder.bind(listCategoryMovies[position], onCategoryClickListener)
    }

    override fun getItemCount(): Int = listCategoryMovies.size

}