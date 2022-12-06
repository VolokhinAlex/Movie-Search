package com.example.java.android1.movie_search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorData(
    val actorName: String,
    val actorPhoto: String
) : Parcelable