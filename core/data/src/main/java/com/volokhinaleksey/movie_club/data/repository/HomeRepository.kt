package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getMovies(
        categoryId: String
    ): Flow<List<Movie>>

    fun getMovies(ids: List<Int>): Flow<List<Movie>>

    suspend fun syncData(categoryId: String, lang: String)
}