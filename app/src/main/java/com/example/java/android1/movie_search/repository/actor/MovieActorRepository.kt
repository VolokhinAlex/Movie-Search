package com.example.java.android1.movie_search.repository.actor

import com.example.java.android1.movie_search.model.ActorDTO

/**
 * The remote repository to get the actors of movie from Remote Server
 */

interface MovieActorRepository {

    suspend fun getMovieActorDetails(
        personId: Long,
        language: String
    ): ActorDTO

}