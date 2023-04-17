package com.example.java.android1.movie_search.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The CastDTO class contains data about the Actor
 * @param biography -       Biography of the actor
 * @param birthday -        Birthday of the actor
 * @param id -              Actor id
 * @param imdb_id -         Id to search for an actor on the imdb site
 * @param name -            Full name of the actor
 * @param place_of_birth -  The actor's place of birth
 * @param popularity -      The popularity of the actor
 * @param profile_path -    Photo of the actor
 */

@Parcelize
data class ActorDTO(
    val biography: String?,
    val birthday: String?,
    val id: Long?,
    val imdb_id: String?,
    val name: String?,
    val place_of_birth: String?,
    val popularity: Double?,
    val profile_path: String?
) : Parcelable