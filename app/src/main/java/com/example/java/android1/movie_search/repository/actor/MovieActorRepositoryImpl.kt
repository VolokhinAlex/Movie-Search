package com.example.java.android1.movie_search.repository.actor

import com.example.java.android1.movie_search.datasource.actor.ActorDataSource
import com.example.java.android1.movie_search.model.ActorDTO

/**
 * Implementation of the interface for getting data from Remote Server
 */

class MovieActorRepositoryImpl(
    private val remoteDataSource: ActorDataSource
) : MovieActorRepository {

    /**
     * Method for getting detailed information about the actor
     * @param personId - Id of the actor to request information for
     * @param language - Response language
     */


    override suspend fun getMovieActorDetails(
        personId: Long,
        language: String
    ): ActorDTO {
        return remoteDataSource.getMovieActorDetails(
            personId = personId,
            language = language
        )
    }

}