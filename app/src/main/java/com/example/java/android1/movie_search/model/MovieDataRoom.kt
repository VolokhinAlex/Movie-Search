package com.example.java.android1.movie_search.model

/**
 * This class is for sqlite local database with using Room
 */

data class MovieDataRoom(
    val movieId: Int?,
    val movieTitle: String?,
    val movieNote: String?,
    val movieFavorite: Boolean?
)
