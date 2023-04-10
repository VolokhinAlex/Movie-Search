package com.example.java.android1.movie_search.datasource.favorite

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.convertMovieEntityToListMovieDataRoom

class LocalFavoriteDataSource(
    private val localDataRoom: MoviesDataBase
) : FavoriteDataSource<MovieDataRoom> {
    override suspend fun getAllFavorites(): List<MovieDataRoom> {
        return convertMovieEntityToListMovieDataRoom(
            entity = localDataRoom.moviesDao().getAllFavorites()
        )
    }
}