package com.volokhinaleksey.movie_club.datasource.home

import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB

interface LocalHomeDataSource : HomeDataSource<CategoryMoviesTMDB> {

    suspend fun saveMovie(moviesTMDB: CategoryMoviesTMDB, category: String)
}