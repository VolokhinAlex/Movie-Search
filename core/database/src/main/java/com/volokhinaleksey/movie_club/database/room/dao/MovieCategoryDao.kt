package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.volokhinaleksey.movie_club.database.room.entity.MovieCategoryEntity

@Dao
interface MovieCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<MovieCategoryEntity>)
}