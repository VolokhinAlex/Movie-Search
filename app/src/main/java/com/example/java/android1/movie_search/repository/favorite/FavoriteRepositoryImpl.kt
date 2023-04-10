package com.example.java.android1.movie_search.repository.favorite

import com.example.java.android1.movie_search.datasource.favorite.FavoriteDataSource
import com.example.java.android1.movie_search.model.MovieDataRoom

class FavoriteRepositoryImpl(
    private val favoriteDataSource: FavoriteDataSource<MovieDataRoom>
) : FavoriteRepository {
    override suspend fun getAllFavorites(): List<MovieDataRoom> {
        return favoriteDataSource.getAllFavorites()
    }
}