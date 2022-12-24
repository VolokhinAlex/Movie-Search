package com.example.java.android1.movie_search.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.BaseMovieCardViewHolder

class SearchAdapter(private val listener: ((MovieDataTMDB) -> Unit)?) : RecyclerView.Adapter<BaseMovieCardViewHolder>() {

    private var movieData: MutableList<MovieDataTMDB> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieData(dataTMDB: List<MovieDataTMDB>) {
        movieData = dataTMDB.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearMovieData() {
        movieData.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieCardViewHolder {
        return BaseMovieCardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.base_movie_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseMovieCardViewHolder, position: Int) {
        holder.bind(movieData[position], listener)
    }

    override fun getItemCount(): Int = movieData.size

}