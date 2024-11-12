package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.data.mappers.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.moviesapi.CoreApi

/**
 * Implementation of the interface for getting data from Remote Server
 */

class HomeApiRepository(
    private val apiHolder: CoreApi
) : HomeRepository {

    override suspend fun getMovies(
        categoryId: String,
        language: String,
        page: Int,
    ): List<MovieUI> {
        return apiHolder.moviesApi.getMoviesByCategory(
            categoryId = categoryId,
            language = language,
            page = page
        ).results.map { it.toMovieUI(categoryId) }
    }

}