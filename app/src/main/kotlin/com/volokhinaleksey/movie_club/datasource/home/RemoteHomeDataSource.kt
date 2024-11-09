package com.volokhinaleksey.movie_club.datasource.home

import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.network.ApiHolder

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