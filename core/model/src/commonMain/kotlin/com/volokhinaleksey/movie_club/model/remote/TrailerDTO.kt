package com.volokhinaleksey.movie_club.model.remote

import com.volokhinaleksey.movie_club.model.ui.Trailer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The Trailer class contains data about the trailer of movie
 * @param name - The name of the movie
 * @param key -  The key to finding the trailer on the YouTube site
 * @param type - Video Type
 * @param id -   Trailer id
 */

@Serializable
data class TrailerDTO(
    @SerialName("name") val name: String? = null,
    @SerialName("key") val key: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("id") val id: String? = null,
)

fun TrailerDTO.toTrailerUI(): Trailer {
    return Trailer(
        id = id.orEmpty(),
        name = name.orEmpty(),
        key = key.orEmpty(),
        type = type.orEmpty()
    )
}