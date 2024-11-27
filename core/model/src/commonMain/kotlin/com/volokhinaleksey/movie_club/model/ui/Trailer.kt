package com.volokhinaleksey.movie_club.model.ui

import com.volokhinaleksey.movie_club.model.Parcelable
import com.volokhinaleksey.movie_club.model.CommonParcelize

@CommonParcelize
data class Trailer(
    val id: String = "",
    val name: String = "",
    val key: String = "",
    val type: String = ""
): Parcelable