package com.example.java.android1.movie_search.datasource.category

import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalCategoryDataSource : CategoryDataSource<List<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}