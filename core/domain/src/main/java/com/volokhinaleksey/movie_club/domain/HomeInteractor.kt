package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import com.volokhinaleksey.movie_club.model.ui.MovieUI

interface HomeInteractor {

    suspend fun getMovies(
        categoryId: String,
        language: String,
        page: Int,
        isLocalSource: Boolean
    ): MovieCategoryState

    suspend fun saveMovie(movies: List<MovieUI>, categoryId: String)

}