package com.example.java.android1.movie_search.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, HistorySearchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao

    abstract fun historySearchDao(): HistorySearchDao

}