package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.MainActivity
import com.example.java.android1.movie_search.view.compose.home.MovieCategory
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment

class MovieHomeBasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val listNamesCategory: TextView
    private val listMoviesCategory: RecyclerView
    private val movieCategoryAdapter = MoviesHomePageAdapter { movieData ->
        val bundle = Bundle()
        bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieData)
        val activity = itemView.context as? MainActivity
        activity?.supportFragmentManager?.replace(
            R.id.container,
            MovieDetailsFragment.newInstance(bundle)
        )
    }

    init {
        listNamesCategory = itemView.findViewById(R.id.title_movie_list)
        listMoviesCategory = itemView.findViewById(R.id.container_for_list_movies)
        listMoviesCategory.adapter = movieCategoryAdapter
        listMoviesCategory.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    fun bind(movieListData: MovieChildListData) {
        when(movieListData.title) {
            MovieCategory.NowPlaying.queryName ->  listNamesCategory.text = MovieCategory.NowPlaying.title
            MovieCategory.TopRated.queryName -> listNamesCategory.text = MovieCategory.TopRated.title
            MovieCategory.Upcoming.queryName -> listNamesCategory.text = MovieCategory.Upcoming.title
            MovieCategory.Popular.queryName -> listNamesCategory.text = MovieCategory.Popular.title
        }
        movieListData.listData?.let { movieCategoryAdapter.setMoviesCategoryData(it) }
    }

}