package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.volokhinaleksey.movie_club.database.room.entity.MovieTrailersEntity

@Dao
interface TrailersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrailers(entities: List<MovieTrailersEntity>)
}