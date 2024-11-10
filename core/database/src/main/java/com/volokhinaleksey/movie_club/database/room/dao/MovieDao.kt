package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity

/**
 * The Interface for interacting with local movie table
 */

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_table WHERE category LIKE :category ORDER BY movie_id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMoviesByCategory(limit: Int, offset: Int, category: String): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE movie_favorite = 1")
    suspend fun getAllFavorites(): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE category LIKE :category")
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE movie_id LIKE :movieId ORDER BY movie_id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMovieByMovieId(limit: Int, offset: Int, movieId: Int): MovieEntity

    @Query("SELECT * FROM movies_table WHERE movie_id LIKE :movieId")
    suspend fun getMovieByMovieId(movieId: Int): MovieEntity

    @Query("SELECT * FROM movies_table WHERE LOWER(movie_title) LIKE '%' || :query || '%' ORDER BY movie_id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMoviesByQuery(limit: Int, offset: Int, query: String): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE LOWER(movie_title) LIKE '%' || :query || '%'")
    suspend fun getMoviesByQuery(query: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<MovieEntity>)

    @Query("UPDATE movies_table SET movie_favorite=:favorite WHERE movie_id = :movieId")
    suspend fun updateFavorite(movieId: Int, favorite: Boolean)

    @Query("UPDATE movies_table SET genres=:genres,runtime=:runtime WHERE movie_id = :movieId")
    suspend fun updateRuntimeAndGenres(movieId: Int, genres: String, runtime: Int)

}