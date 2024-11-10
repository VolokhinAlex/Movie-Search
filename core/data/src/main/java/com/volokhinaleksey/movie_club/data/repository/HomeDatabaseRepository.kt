package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI

interface HomeDatabaseRepository : HomeRepository {
    suspend fun saveMovie(movies: List<MovieUI>, categoryId: String)
}

class HomeDatabaseRepositoryImpl(
    private val database: MoviesDataBase,
) : HomeDatabaseRepository {

    override suspend fun getMovies(categoryId: String, language: String, page: Int): List<MovieUI> {
        return database.moviesDao().getMoviesByCategory(categoryId).map {
            val trailers = database.trailerDao().getTrailerById(it.movieId ?: 0)
            val actors = database.actorDao().getActorsByMovieId(it.movieId ?: 0)
            it.toMovieUI(trailers, actors)
        }
    }

    override suspend fun saveMovie(movies: List<MovieUI>, categoryId: String) {
        database.moviesDao().insert(movies.map { it.asEntity(categoryId) })
    }
}