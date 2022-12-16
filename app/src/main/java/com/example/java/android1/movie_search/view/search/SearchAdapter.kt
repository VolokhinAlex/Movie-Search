package com.example.java.android1.movie_search.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB

class SearchAdapter(private val listener: ((MovieDataTMDB) -> Unit)?) : RecyclerView.Adapter<SearchViewHolder>() {

    private var movieData: List<MovieDataTMDB> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieData(dataTMDB: List<MovieDataTMDB>) {
        movieData = dataTMDB
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_search_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movieData[position], listener)
    }

    override fun getItemCount(): Int = movieData.size

}