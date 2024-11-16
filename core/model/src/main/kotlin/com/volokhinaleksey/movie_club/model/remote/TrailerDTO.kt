package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.volokhinaleksey.movie_club.model.ui.Trailer
import kotlinx.parcelize.Parcelize

/**
 * The Trailer class contains data about the trailer of movie
 * @param name - The name of the movie
 * @param key -  The key to finding the trailer on the YouTube site
 * @param type - Video Type
 * @param id -   Trailer id
 */

@Parcelize
data class TrailerDTO(
    val name: String?,
    val key: String?,
    val type: String?,
    val id: String?
) : Parcelable

fun TrailerDTO.toTrailerUI(): Trailer {
    return Trailer(
        id = id.orEmpty(),
        name = name.orEmpty(),
        key = key.orEmpty(),
        type = type.orEmpty()
    )
}