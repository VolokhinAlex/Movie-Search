package com.example.java.android1.movie_search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The class is responsible for storing information about the movie's main actors.
 *  @param actorName - The full name of the movie's actor
 *  @param actorPhoto - The profile photo of the movie's actor
 */

@Parcelize
data class ActorData(
    val actorName: String?,
    val actorPhoto: String?
) : Parcelable