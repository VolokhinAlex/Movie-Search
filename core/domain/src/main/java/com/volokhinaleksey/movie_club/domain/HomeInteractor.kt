package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    fun getMovies(categoryId: String): Flow<List<Movie>>
    suspend fun syncData(category: MovieCategory, lang: String)
}