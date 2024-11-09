package com.volokhinaleksey.movie_club.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.movie_club.model.local.ActorEntity
import com.volokhinaleksey.movie_club.model.local.MovieEntity
import com.volokhinaleksey.movie_club.model.local.SimilarMovieEntity
import com.volokhinaleksey.movie_club.model.local.TrailerEntity

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