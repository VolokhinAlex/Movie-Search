package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.Movie

sealed interface DetailsMovieState {
    data class Success(val data: Movie) : DetailsMovieState
    data class Error(val message: String) : DetailsMovieState
    data object Loading : DetailsMovieState
}