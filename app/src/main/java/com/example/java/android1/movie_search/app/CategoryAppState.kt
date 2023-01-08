package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.app.CategoryAppState.*
import com.example.java.android1.movie_search.model.CategoryMoviesData

/**
 * States that come from the remote server. Total 3 states
 * 1. [Success] - If the request is successful, stores a category of movies.
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class CategoryAppState {
    data class Success(val data: CategoryMoviesData) : CategoryAppState()
    data class Error(val error: Throwable) : CategoryAppState()
    object Loading : CategoryAppState()
}
