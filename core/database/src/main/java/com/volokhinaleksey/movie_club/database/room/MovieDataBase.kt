package com.volokhinaleksey.movie_club.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.movie_club.database.room.dao.MovieDao
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
}