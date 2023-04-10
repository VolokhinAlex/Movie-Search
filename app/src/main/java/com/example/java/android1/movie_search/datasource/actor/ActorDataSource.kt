package com.example.java.android1.movie_search.datasource.actor

import com.example.java.android1.movie_search.model.ActorDTO

interface ActorDataSource {

    suspend fun getMovieActorDetails(
        personId: Long,
        language: String
    ): ActorDTO

}