package com.example.java.android1.movie_search.view

import com.example.java.android1.movie_search.model.MovieDataTMDB

interface MovieDataLoadListener {
    fun onLoaded(movieData: MovieDataTMDB)
    fun onFailed(e: Throwable)
}