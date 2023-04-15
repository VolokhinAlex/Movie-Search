package com.example.java.android1.movie_search.datasource.home

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.network.ApiHolder

class RemoteHomeDataSource(
    private val apiHolder: ApiHolder
) : HomeDataSource<CategoryMoviesTMDB> {

    override suspend fun getMovies(
        category: String,
        language: String,
        page: Int
    ): CategoryMoviesTMDB {
        return apiHolder.moviesApi.getCategoryMovies(
            category = category,
            language = language,
            page = page
        )
    }


}