package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.volokhinaleksey.movie_club.model.ui.ActorUI
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

fun CastDTO.toActorUI(): ActorUI {
    return ActorUI(
        actorId = id ?: 0,
        name = name.orEmpty(),
        character = character.orEmpty(),
        profilePath = profile_path.orEmpty()
    )
}
