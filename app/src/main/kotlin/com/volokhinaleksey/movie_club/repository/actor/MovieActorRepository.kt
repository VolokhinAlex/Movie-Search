package com.volokhinaleksey.movie_club.repository.actor

import com.volokhinaleksey.movie_club.model.state.ActorState

/**
 * The remote repository to get the actors of movie from Remote Server
 */

interface MovieActorRepository {

    suspend fun getMovieActorDetails(
        personId: Long,
        language: String,
        isNetworkAvailable: Boolean
    ): ActorState

}