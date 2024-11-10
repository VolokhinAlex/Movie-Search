package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.model.ui.MovieUI

/**
 * The remote repository to get the categories of movies from Remote Server
 */

interface HomeRepository {

    suspend fun getMovies(
        categoryId: String,
        language: String,
        page: Int
    ): List<MovieUI>

}