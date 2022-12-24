package com.example.java.android1.movie_search.viewmodel

import com.example.java.android1.movie_search.model.MovieDataTMDB

sealed class AppState {
    data class Success(val data: List<MovieDataTMDB>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}