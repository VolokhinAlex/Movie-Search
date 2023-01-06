package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.MovieDataTMDB

sealed class MovieAppState {
    data class Success(val data: List<MovieDataTMDB>) : MovieAppState()
    data class Error(val error: Throwable) : MovieAppState()
    object Loading : MovieAppState()
}