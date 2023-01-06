package com.example.java.android1.movie_search.view.compose.home

sealed class CategoryAppState {
    data class Success(val data: Category) : CategoryAppState()
    data class Error(val error: Throwable) : CategoryAppState()
    object Loading : CategoryAppState()
}
