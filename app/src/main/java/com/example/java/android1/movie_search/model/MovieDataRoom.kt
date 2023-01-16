package com.example.java.android1.movie_search.model

/**
 * This class is for sqlite local database with using Room
 * @param movieId - movie ID
 * @param movieTitle - The title of a particular movie
 * @param movieNote - A note about the movie
 * @param movieFavorite - Favorite movie.
 * @param moviePoster - Movie Image
 * @param movieReleaseDate - Release date of the movie
 * @param movieRating - Rating of the movie from 0 to 10
 */

data class MovieDataRoom(
    val movieId: Int?,
    val movieTitle: String?,
    val movieNote: String?,
    val movieFavorite: Boolean?,
    val moviePoster: String?,
    val movieReleaseDate: String?,
    val movieRating: Double?
)
