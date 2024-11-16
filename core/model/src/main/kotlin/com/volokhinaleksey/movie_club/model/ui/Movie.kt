package com.volokhinaleksey.movie_club.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val posterPath: String = "",
    val id: Int = 0,
    val imdbId: String = "",
    val genres: List<Genre> = emptyList(),
    val originalLanguage: String = "",
    val overview: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val releaseDate: String = "",
    val runtime: String = "",
    val actors: List<Actor> = emptyList(),
    val videos: List<Trailer> = emptyList(),
    val favorite: Boolean = false,
    val category: String = ""
): Parcelable