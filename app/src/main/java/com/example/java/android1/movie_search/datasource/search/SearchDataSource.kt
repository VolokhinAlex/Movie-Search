package com.example.java.android1.movie_search.datasource.search

import kotlinx.coroutines.flow.Flow

interface SearchDataSource<T> {

    fun getMoviesByQuery(query: String): Flow<T>

}