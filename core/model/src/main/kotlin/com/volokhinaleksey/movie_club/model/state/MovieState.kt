package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.Movie

sealed interface MovieState {
    data class Success(val data: List<Movie>) : MovieState
    data class Error(val errorMessage: Throwable) : MovieState
    data object Loading : MovieState
}

sealed interface DetailsMovieState {
    data class Success(val data: Movie) : DetailsMovieState
    data class Error(val message: String) : DetailsMovieState
    data object Loading : DetailsMovieState
}