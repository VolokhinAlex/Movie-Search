package com.example.java.android1.movie_search.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.java.android1.movie_search.model.local.ActorEntity
import com.example.java.android1.movie_search.model.local.MovieEntity
import com.example.java.android1.movie_search.model.local.SimilarMovieEntity
import com.example.java.android1.movie_search.model.local.TrailerEntity

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [MovieEntity::class, TrailerEntity::class, ActorEntity::class, SimilarMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
    abstract fun trailerDao(): TrailerDao
    abstract fun actorDao(): ActorDao
    abstract fun similarMovieDao(): SimilarMovieDao

}