package com.example.java.android1.movie_search.datasource.home

import com.example.java.android1.movie_search.model.remote.CategoryMoviesTMDB

interface LocalHomeDataSource : HomeDataSource<CategoryMoviesTMDB> {

    suspend fun saveMovie(moviesTMDB: CategoryMoviesTMDB, category: String)
}