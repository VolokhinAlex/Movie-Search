package com.example.java.android1.movie_search.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.BaseMovieCardViewHolder

class MoviesHomePageAdapter(
    private var onItemClickListener: ((MovieDataTMDB) -> Unit)?
) : RecyclerView.Adapter<BaseMovieCardViewHolder>() {

    private var listMoviesCategory: List<MovieDataTMDB> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesCategoryData(listMoviesCategory: List<MovieDataTMDB>) {
        this.listMoviesCategory = listMoviesCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieCardViewHolder {
        return BaseMovieCardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.base_movie_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseMovieCardViewHolder, position: Int) {
        holder.bind(listMoviesCategory[position], onItemClickListener)
    }

    override fun getItemCount(): Int = listMoviesCategory.size

}