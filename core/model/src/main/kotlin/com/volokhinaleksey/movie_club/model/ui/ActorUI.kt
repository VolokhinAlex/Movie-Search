package com.volokhinaleksey.movie_club.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorUI(
    val actorId: Long = 0,
    val movieId: Long = 0,
    val biography: String = "",
    val birthday: String = "",
    val imdbId: String = "",
    val name: String = "",
    val placeOfBirth: String = "",
    val popularity: Double = 0.0,
    val profilePath: String = "",
    val character: String = ""
): Parcelable
