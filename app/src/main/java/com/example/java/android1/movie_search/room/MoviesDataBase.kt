package com.example.java.android1.movie_search.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao

}