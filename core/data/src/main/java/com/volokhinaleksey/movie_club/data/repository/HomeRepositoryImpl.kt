package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val database: MovieDataBase,
    private val apiHolder: CoreApi
) : HomeRepository {

    override fun getMovies(
        categoryId: String
    ): Flow<List<Movie>> = database.moviesDao().getNewsResources(categoryId)
        .map { it.map(MovieEntity::asExternalModel) }

    override suspend fun syncData(categoryId: String, lang: String) {
        val movies = apiHolder.moviesApi.getMoviesByCategory(
            categoryId = categoryId,
            language = lang
        ).results.map { it.asEntity(categoryId) }

        database.moviesDao().upsertAllMovies(movies)
    }
}