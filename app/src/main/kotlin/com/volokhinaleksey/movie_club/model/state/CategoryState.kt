package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.CategoryMovieUI

sealed interface CategoryState {
    data class Success(val data: CategoryMovieUI) : CategoryState
    data class Error(val errorMessage: Throwable) : CategoryState
    object Loading : CategoryState
}