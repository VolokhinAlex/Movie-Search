package com.volokhinaleksey.movie_club.repository.favorite

import com.volokhinaleksey.movie_club.datasource.favorite.FavoriteDataSource
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.utils.mapLocalMovieToMovieUI

class FavoriteRepositoryImpl(
    private val favoriteDataSource: FavoriteDataSource<LocalMovieData>
) : FavoriteRepository {
    override suspend fun getAllFavorites(): MovieState {
        return MovieState.Success(
            favoriteDataSource.getAllFavorites().map { mapLocalMovieToMovieUI(it) })
    }
}