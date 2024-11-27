package com.volokhinaleksey.movie_club.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The class is needed to get a list of trailers
 * @param results - List of movie trailers
 */

@Serializable
data class VideosDTO(
    @SerialName("results") val results: List<TrailerDTO>
)