package com.volokhinaleksey.movie_club.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.movie_club.database.room.dao.GenresDao
import com.volokhinaleksey.movie_club.database.room.dao.MovieDao
import com.volokhinaleksey.movie_club.database.room.entity.GenreEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        MoviesGenresEntity::class
    ],
    version = 2
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
    abstract fun genresDao(): GenresDao
}