package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.MovieUI

sealed interface MovieCategoryState {
    data class Success(val data: List<MovieUI>) : MovieCategoryState
    data class Error(val errorMessage: Throwable) : MovieCategoryState
    data object Loading : MovieCategoryState
}