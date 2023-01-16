package com.example.java.android1.movie_search.app

sealed class MovieCategory(val queryName: String, val title: String) {
    object NowPlaying : MovieCategory("now_playing", "Now Playing")
    object Popular : MovieCategory("popular", "Popular")
    object TopRated : MovieCategory("top_rated", "Top Rated")
    object Upcoming : MovieCategory("upcoming", "Upcoming")
}
