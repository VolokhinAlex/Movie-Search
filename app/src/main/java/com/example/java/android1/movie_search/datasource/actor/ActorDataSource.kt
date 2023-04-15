package com.example.java.android1.movie_search.datasource.actor

interface ActorDataSource<T> {

    suspend fun getMovieActorDetails(
        personId: Long,
        language: String
    ): T

}