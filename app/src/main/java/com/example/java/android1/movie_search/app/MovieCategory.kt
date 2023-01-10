package com.example.java.android1.movie_search.app

import androidx.annotation.StringRes
import com.example.java.android1.movie_search.R

/**
 * List of available categories in TMDB API
 */

sealed class MovieCategory(val queryName: String, @StringRes val title: Int) {
    object NowPlaying : MovieCategory(queryName = "now_playing", title = R.string.now_playing)
    object Popular : MovieCategory(queryName = "popular", title = R.string.popular)
    object TopRated : MovieCategory(queryName = "top_rated", title = R.string.top_rated)
    object Upcoming : MovieCategory(queryName = "upcoming", title = R.string.upcoming)
}
