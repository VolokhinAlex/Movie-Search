package com.example.java.android1.movie_search.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The classes CreditsDTO and CastDTO need to get Subcategory "credits" and "cast" from MovieDataTMDB
 * @param cast - List of movie actors
 */

@Parcelize
data class CreditsDTO(val cast: List<CastDTO>) : Parcelable