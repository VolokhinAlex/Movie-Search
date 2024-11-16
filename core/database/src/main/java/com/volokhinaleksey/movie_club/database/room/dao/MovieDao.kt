package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.MovieDetails
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity
import kotlinx.coroutines.flow.Flow

/**
 * The Interface for interacting with local movie table
 */

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(entity: MovieEntity)

    @Upsert
    suspend fun upsertAllMovies(entity: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :categoryId")
    fun getMoviesByCategory(categoryId: String): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieDetailsById(movieId: Int): MovieDetails?

    @Upsert
    suspend fun upsertMoviesGenres(moviesGenresEntities: List<MoviesGenresEntity>)
}