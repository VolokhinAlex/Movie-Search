package com.volokhinaleksey.movie_club.datasource.favorite

import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.room.MoviesDataBase
import com.volokhinaleksey.movie_club.utils.convertMovieEntityToListMovieDataRoom

class LocalFavoriteDataSource(
    private val localDataRoom: MoviesDataBase
) : FavoriteDataSource<LocalMovieData> {
    override suspend fun getAllFavorites(): List<LocalMovieData> {
        return convertMovieEntityToListMovieDataRoom(
            entity = localDataRoom.moviesDao().getAllFavorites()
        )
    }
}