package com.volokhinaleksey.movie_club.datasource.favorite

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.model.local.LocalMovieData

class LocalFavoriteDataSource(
    private val localDataRoom: MovieDataBase
) : FavoriteDataSource<LocalMovieData> {
    override suspend fun getAllFavorites(): List<LocalMovieData> {
        TODO()
    }
}