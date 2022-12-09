package com.example.java.android1.movie_search.viewmodel

import com.example.java.android1.movie_search.model.MovieData

sealed class AppState {
    data class Success(val data: List<MovieData>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}