package com.volokhinaleksey.movie_club.repository.home

import com.volokhinaleksey.movie_club.model.state.CategoryState

/**
 * The remote repository to get the categories of movies from Remote Server
 */

interface HomeRepository {

    suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int,
        isNetworkAvailable: Boolean
    ): CategoryState

}