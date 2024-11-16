package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.volokhinaleksey.movie_club.database.room.entity.MovieTrailersEntity

@Dao
interface TrailersDao {
    @Insert
    suspend fun insertTrailers(entities: List<MovieTrailersEntity>)
}