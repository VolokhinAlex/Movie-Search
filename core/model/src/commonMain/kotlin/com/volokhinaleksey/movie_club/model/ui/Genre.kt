package com.volokhinaleksey.movie_club.model.ui

import com.volokhinaleksey.movie_club.model.Parcelable
import com.volokhinaleksey.movie_club.model.CommonParcelize

@CommonParcelize
data class Genre(
    val id: Int = 0,
    val name: String,
): Parcelable