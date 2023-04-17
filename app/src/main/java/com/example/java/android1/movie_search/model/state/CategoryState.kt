package com.example.java.android1.movie_search.model.state

import com.example.java.android1.movie_search.model.ui.CategoryMovieUI

sealed interface CategoryState {
    data class Success(val data: CategoryMovieUI) : CategoryState
    data class Error(val errorMessage: Throwable) : CategoryState
    object Loading : CategoryState
}