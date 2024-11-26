package com.volokhinaleksey.movie_club.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The CastDTO class contains data about the Actor
 * @param biography Biography of the actor
 * @param birthday Birthday of the actor
 * @param id Actor id
 * @param imdbId Id to search for an actor on the imdb site
 * @param name Full name of the actor
 * @param placeOfBirth The actor's place of birth
 * @param popularity The popularity of the actor
 * @param profilePath Photo of the actor
 */

@Serializable
data class ActorDTO(
    @SerialName("biography") val biography: String? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("id") val id: Long? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("character") val character: String? = null,
)

@Serializable
data class ActorsDTO(@SerialName("cast") val cast: List<ActorDTO>)