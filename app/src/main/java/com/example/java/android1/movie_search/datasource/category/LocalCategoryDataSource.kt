package com.example.java.android1.movie_search.datasource.category

import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalCategoryDataSource {

    suspend fun saveMovie(movie: LocalMovieData)

    suspend fun getMoviesByCategory(category: String): List<LocalMovieData>
}