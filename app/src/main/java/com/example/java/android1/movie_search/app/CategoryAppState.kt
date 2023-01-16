package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.CategoryData

sealed class CategoryAppState {
    data class Success(val data: CategoryData) : CategoryAppState()
    data class Error(val error: Throwable) : CategoryAppState()
    object Loading : CategoryAppState()
}
