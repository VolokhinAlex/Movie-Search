package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * The Interface for interacting with local movie table
 */

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAllMovies(entity: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :categoryId")
    fun getNewsResources(categoryId: String): Flow<List<MovieEntity>>
}