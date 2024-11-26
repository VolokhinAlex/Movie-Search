package com.volokhinaleksey.movie_club.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDTO(
    @SerialName("genres") val genres: List<GenreDTO>,
)

/**
 * The class GenresDTO needs to get Subcategory "genres" from MovieDataTMDB
 * @param id ID of the movie genre
 * @param name name of the movie genre
 */

@Serializable
data class GenreDTO(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
)