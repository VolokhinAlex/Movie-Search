package com.volokhinaleksey.movie_club.model.local

data class LocalActorData(
    val actorId: Long,
    val movieId: Long?,
    val biography: String?,
    val birthday: String?,
    val imdbId: String?,
    val name: String?,
    val placeOfBirth: String?,
    val popularity: Double?,
    val profilePath: String?,
    val character: String?
)