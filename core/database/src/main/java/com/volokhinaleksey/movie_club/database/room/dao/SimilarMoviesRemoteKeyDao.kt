package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieRemoteKey

@Dao
interface SimilarMoviesRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<SimilarMovieRemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(key: SimilarMovieRemoteKey)

    @Query("SELECT * FROM similar_movie_remote_key WHERE movie_id = :movieId")
    suspend fun getKeyByMovieId(movieId: Int): SimilarMovieRemoteKey?

    @Query("DELETE FROM similar_movie_remote_key WHERE movie_id = :movieId")
    suspend fun deleteByMovieId(movieId: Int)
}