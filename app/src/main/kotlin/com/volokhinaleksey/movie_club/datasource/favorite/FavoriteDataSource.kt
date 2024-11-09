package com.volokhinaleksey.movie_club.datasource.favorite

interface FavoriteDataSource<T> {

    suspend fun getAllFavorites(): List<T>

}