package com.volokhinaleksey.movie_club.model.ui

import com.volokhinaleksey.movie_club.model.Parcelable
import com.volokhinaleksey.movie_club.model.CommonParcelize

@CommonParcelize
data class Favorite(
    val movieId: Int,
    val isFavorite: Boolean
): Parcelable