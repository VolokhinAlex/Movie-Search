package com.volokhinaleksey.movie_club.datasource.actor

interface ActorDataSource<T> {

    suspend fun getMovieActorDetails(
        personId: Long,
        language: String
    ): T

}