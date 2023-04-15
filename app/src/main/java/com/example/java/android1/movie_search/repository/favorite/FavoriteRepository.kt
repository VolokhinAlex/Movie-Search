package com.example.java.android1.movie_search.repository.favorite

import com.example.java.android1.movie_search.model.state.MovieState

interface FavoriteRepository {

    suspend fun getAllFavorites(): MovieState

}