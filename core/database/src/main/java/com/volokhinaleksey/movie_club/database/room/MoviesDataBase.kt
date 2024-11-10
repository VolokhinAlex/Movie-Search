package com.volokhinaleksey.movie_club.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.movie_club.database.room.dao.ActorDao
import com.volokhinaleksey.movie_club.database.room.dao.MovieDao
import com.volokhinaleksey.movie_club.database.room.dao.SimilarMovieDao
import com.volokhinaleksey.movie_club.database.room.dao.TrailerDao
import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.TrailerEntity

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [MovieEntity::class, TrailerEntity::class, ActorEntity::class, SimilarMovieEntity::class],
    version = 1
)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
    abstract fun trailerDao(): TrailerDao
    abstract fun actorDao(): ActorDao
    abstract fun similarMovieDao(): SimilarMovieDao
}