package com.example.java.android1.movie_search.datasource.search

import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalSearchDataSource : SearchDataSource<List<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}