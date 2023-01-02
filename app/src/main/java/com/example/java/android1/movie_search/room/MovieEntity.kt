package com.example.java.android1.movie_search.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The model of local movie table
 */

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int?,
    @ColumnInfo(name = "movie_title")
    val movieTitle: String?,
    @ColumnInfo(name = "movie_note")
    val movieNote: String?,
    @ColumnInfo(name = "movie_favorite")
    val movieFavorite: Boolean?,
    @ColumnInfo(name = "movie_poster")
    val moviePoster: String?,
    @ColumnInfo(name = "movie_release_date")
    val movieReleaseDate: String?,
    @ColumnInfo(name = "movie_rating")
    val movieRating: Double?
)
