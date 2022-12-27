package com.example.java.android1.movie_search.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieChildListData

class MoviesHomeBasicAdapter : RecyclerView.Adapter<MovieHomeBasicViewHolder>() {

    private var list: List<MovieChildListData> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setParentListData(data: List<MovieChildListData>) {
        list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHomeBasicViewHolder {
        return MovieHomeBasicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_home_basic_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHomeBasicViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}