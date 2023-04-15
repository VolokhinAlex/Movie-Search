package com.example.java.android1.movie_search.repository.favorite

import com.example.java.android1.movie_search.datasource.favorite.FavoriteDataSource
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.model.state.MovieState
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieUI

class FavoriteRepositoryImpl(
    private val favoriteDataSource: FavoriteDataSource<LocalMovieData>
) : FavoriteRepository {
    override suspend fun getAllFavorites(): MovieState {
        return MovieState.Success(
            favoriteDataSource.getAllFavorites().map { mapLocalMovieToMovieUI(it) })
    }
}