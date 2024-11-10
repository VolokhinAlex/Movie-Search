package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieEntity

/**
 * The Interface for interacting with local similar movie table
 */

@Dao
interface SimilarMovieDao {

    @Query("SELECT * FROM similar_movie WHERE movie_id LIKE :movieId")
    suspend fun getSimilarMoviesByMovieId(movieId: Long): List<SimilarMovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SimilarMovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<SimilarMovieEntity>)

    @Update
    suspend fun update(entity: SimilarMovieEntity)

}