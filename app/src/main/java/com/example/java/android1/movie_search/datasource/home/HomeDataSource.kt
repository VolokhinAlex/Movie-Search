package com.example.java.android1.movie_search.datasource.home

interface HomeDataSource<T> {

    suspend fun getMovies(
        category: String,
        language: String,
        page: Int
    ): T

}