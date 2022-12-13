package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.view.MainActivity
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment

class MovieHomeBasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val listTitle: TextView
    private val listOfMovies: RecyclerView
    private val adapter = MoviesHomePageAdapter { movieData ->
        val bundle = Bundle()
        bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieData)
        val activity = itemView.context as? MainActivity
        activity?.supportFragmentManager?.replace(
            R.id.container,
            MovieDetailsFragment.newInstance(bundle)
        )
    }

    init {
        listTitle = itemView.findViewById(R.id.title_movie_list)
        listOfMovies = itemView.findViewById(R.id.container_for_list_movies)
        listOfMovies.adapter = adapter
        listOfMovies.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    fun bind(movieListData: MovieChildListData) {
        listTitle.text = movieListData.title
        adapter.setMovieData(movieListData.listData)
    }

}