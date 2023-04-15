package com.example.java.android1.movie_search.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The CastDTO class contains data about the character
 * @param id -              Actor id
 * @param name -            Full name of the actor
 * @param profile_path -    Photo of the actor
 * @param character -       Character Name
 */

@Parcelize
data class CastDTO(
    val id: Long?,
    val name: String?,
    val profile_path: String?,
    val character: String?
) : Parcelable