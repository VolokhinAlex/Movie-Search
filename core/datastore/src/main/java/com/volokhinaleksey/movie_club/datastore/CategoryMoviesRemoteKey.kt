package com.volokhinaleksey.movie_club.datastore

data class CategoryMoviesRemoteKey(
    val category: String,
    val nextKey: Int
)