package com.volokhinaleksey.movie_club.datasource.home

interface HomeDataSource<T> {

    suspend fun getMovies(
        category: String,
        language: String,
        page: Int
    ): T

}