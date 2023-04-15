package com.example.java.android1.movie_search.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Interface for interacting with local movie table
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_table WHERE movie_favorite = 1")
    suspend fun getAllFavorites(): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE movie_id LIKE :movieId")
    suspend fun getMovieByMovieId(movieId: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: MovieEntity)

    @Query("UPDATE movies_table SET movie_favorite=:favorite WHERE movie_id = :movieId")
    suspend fun updateFavorite(movieId: Int, favorite: Boolean)

    @Query("UPDATE movies_table SET movie_note=:note WHERE movie_id = :movieId")
    suspend fun updateNote(movieId: Int, note: String)

}