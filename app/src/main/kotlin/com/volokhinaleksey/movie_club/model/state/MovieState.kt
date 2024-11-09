package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.MovieUI

sealed interface MovieState {
    data class Success(val data: List<MovieUI>) : MovieState
    data class Error(val errorMessage: Throwable) : MovieState
    object Loading : MovieState
}