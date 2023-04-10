package com.example.java.android1.movie_search.datasource.favorite

interface FavoriteDataSource<T> {

    suspend fun getAllFavorites(): List<T>

}