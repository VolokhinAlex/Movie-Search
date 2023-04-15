package com.example.java.android1.movie_search.datasource.home

import com.example.java.android1.movie_search.model.old.remote.CategoryMoviesTMDB

interface LocalHomeDataSource : HomeDataSource<CategoryMoviesTMDB> {

    suspend fun saveMovie(moviesTMDB: CategoryMoviesTMDB, category: String)
}