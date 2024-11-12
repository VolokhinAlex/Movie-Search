package com.volokhinaleksey.movie_club.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUI(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val posterPath: String = "",
    val budget: Int = 0,
    val id: Int = 0,
    val imdbId: String = "",
    val genres: List<GenreUI> = emptyList(),
    val originalLanguage: String = "",
    val overview: String = "",
    val title: String = "",
    val voteCount: Int = 0,
    val voteAverage: Double = 0.0,
    val releaseDate: String = "",
    val runtime: String = "",
    val actors: List<ActorUI> = emptyList(),
    val videos: List<TrailerUI> = emptyList(),
    val favorite: Boolean = false,
    val category: String = ""
): Parcelable