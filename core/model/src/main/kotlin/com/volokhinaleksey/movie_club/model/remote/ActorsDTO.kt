package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The classes CreditsDTO and CastDTO need to get Subcategory "credits" and "cast" from MovieDataTMDB
 * @param cast - List of movie actors
 */

@Parcelize
data class ActorsDTO(val cast: List<ActorDTO>) : Parcelable