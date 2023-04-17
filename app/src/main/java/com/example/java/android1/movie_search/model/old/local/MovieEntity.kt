package com.example.java.android1.movie_search.model.old.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The model of local movie table
 * @param movieId - movie ID
 * @param movieTitle - The title of a particular movie
 * @param movieFavorite - Favorite movie. Values 1 (True) or 0 (False)
 * @param moviePoster - Movie Image
 * @param movieReleaseDate - Release date of the movie
 * @param movieRating - Rating of the movie from 0 to 10
 */

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int?,
    @ColumnInfo(name = "movie_title")
    val movieTitle: String?,
    @ColumnInfo(name = "movie_favorite")
    val movieFavorite: Boolean?,
    @ColumnInfo(name = "movie_poster")
    val moviePoster: String?,
    @ColumnInfo(name = "movie_release_date")
    val movieReleaseDate: String?,
    @ColumnInfo(name = "movie_rating")
    val movieRating: Double?,
    val runtime: Int?,
    val genres: String,
    val overview: String?,
    val category: String,
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,
)
