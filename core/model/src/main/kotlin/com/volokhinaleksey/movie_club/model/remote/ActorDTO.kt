package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class ActorDTO(
    val biography: String?,
    val birthday: String?,
    val id: Long?,
    @SerializedName("imdb_id") val imdbId: String?,
    val name: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?,
    val character: String? = null,
) : Parcelable