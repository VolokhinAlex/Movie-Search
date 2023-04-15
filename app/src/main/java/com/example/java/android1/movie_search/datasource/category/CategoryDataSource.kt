package com.example.java.android1.movie_search.datasource.category

import kotlinx.coroutines.flow.Flow

interface CategoryDataSource<T> {

    fun getCategoryMovies(categoryMovies: String): Flow<T>

}