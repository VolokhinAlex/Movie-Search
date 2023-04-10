package com.example.java.android1.movie_search.repository.favorite

import com.example.java.android1.movie_search.model.MovieDataRoom

interface FavoriteRepository {

    suspend fun getAllFavorites(): List<MovieDataRoom>

}