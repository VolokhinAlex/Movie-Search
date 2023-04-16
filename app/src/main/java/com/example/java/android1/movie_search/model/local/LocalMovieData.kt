package com.example.java.android1.movie_search.model.local

/**
 * This class is for sqlite local database with using Room
 * @param movieId - movie ID
 * @param movieTitle - The title of a particular movie
 * @param movieFavorite - Favorite movie.
 * @param moviePoster - Movie Image
 * @param movieReleaseDate - Release date of the movie
 * @param movieRating - Rating of the movie from 0 to 10
 */

data class LocalMovieData(
    val movieId: Int?,
    val movieTitle: String?,
    val movieFavorite: Boolean?,
    val moviePoster: String?,
    val movieReleaseDate: String?,
    val movieRating: Double?,
    val runtime: Int?,
    val genres: String,
    val overview: String?,
    val video: List<LocalTrailerData>,
    val actor: List<LocalActorData>,
    val category: String,
    val imdbId: String?,
    val adult: Boolean?,
    val backdropPath: String?,
    val originalLanguage: String?,
    val voteCount: Int?,
)
