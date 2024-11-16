package com.volokhinaleksey.movie_club.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.movie_club.database.room.dao.ActorsDao
import com.volokhinaleksey.movie_club.database.room.dao.FavoritesDao
import com.volokhinaleksey.movie_club.database.room.dao.GenresDao
import com.volokhinaleksey.movie_club.database.room.dao.MovieDao
import com.volokhinaleksey.movie_club.database.room.dao.TrailersDao
import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity
import com.volokhinaleksey.movie_club.database.room.entity.FavoriteEntity
import com.volokhinaleksey.movie_club.database.room.entity.GenreEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieTrailersEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity

/**
 * The main class for combining table models (Entity) and interfaces (Dao)
 */

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        MoviesGenresEntity::class,
        FavoriteEntity::class,
        ActorEntity::class,
        MovieTrailersEntity::class
    ],
    version = 4
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
    abstract fun genresDao(): GenresDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun actorsDao(): ActorsDao
    abstract fun trailersDao(): TrailersDao
}