package com.example.java.android1.movie_search.model.state

import com.example.java.android1.movie_search.model.ui.MovieUI

sealed interface MovieState {
    data class Success(val data: List<MovieUI>) : MovieState
    data class Error(val errorMessage: Throwable) : MovieState
    object Loading : MovieState
}