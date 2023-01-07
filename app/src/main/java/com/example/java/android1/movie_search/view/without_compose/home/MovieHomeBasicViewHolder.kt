package com.example.java.android1.movie_search.view.without_compose.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.without_compose.MainActivity
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.view.without_compose.details.MovieDetailsFragment

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
    private val categoryMoviesListButton: AppCompatImageView

    init {
        listNamesCategory = itemView.findViewById(R.id.title_movie_list)
        listMoviesCategory = itemView.findViewById(R.id.container_for_list_movies)
        listMoviesCategory.adapter = movieCategoryAdapter
        listMoviesCategory.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        categoryMoviesListButton = itemView.findViewById(R.id.action_all_list_of_movies)
    }

    fun bind(movieListData: MovieChildListData, onCategoryClickListener: (String) -> Unit) {
        when(movieListData.title) {
            MovieCategory.NowPlaying.queryName ->  listNamesCategory.text = MovieCategory.NowPlaying.title
            MovieCategory.TopRated.queryName -> listNamesCategory.text = MovieCategory.TopRated.title
            MovieCategory.Upcoming.queryName -> listNamesCategory.text = MovieCategory.Upcoming.title
            MovieCategory.Popular.queryName -> listNamesCategory.text = MovieCategory.Popular.title
        }
        movieListData.listData?.let { movieCategoryAdapter.setMoviesCategoryData(it) }
        categoryMoviesListButton.setOnClickListener {
            movieListData.title?.let { categoryName -> onCategoryClickListener.invoke(categoryName) }
        }
    }

}