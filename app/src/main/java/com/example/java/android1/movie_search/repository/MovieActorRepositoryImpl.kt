package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from Remote Server
 */

class MovieActorRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieActorRepository {

    /**
     * Method for getting detailed information about the actor
     * @param personId - Id of the actor to request information for
     * @param language - Response language
     */

    override fun getMovieActorDetailsFromRemoteServer(
        personId: Long,
        language: String,
        callback: Callback<ActorDTO>
    ) {
        remoteDataSource.getActorData(
            personId = personId,
            language = language,
            callback = callback
        )
    }

}