package com.volokhinaleksey.movie_club.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trailer(
    val id: String = "",
    val name: String = "",
    val key: String = "",
    val type: String = ""
): Parcelable