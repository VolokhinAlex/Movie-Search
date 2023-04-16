package com.example.java.android1.movie_search.repository.actor

import com.example.java.android1.movie_search.datasource.actor.ActorDataSource
import com.example.java.android1.movie_search.datasource.actor.LocalActorDataSource
import com.example.java.android1.movie_search.model.remote.ActorDTO
import com.example.java.android1.movie_search.model.state.ActorState
import com.example.java.android1.movie_search.utils.mapActorDtoToActorUI
import com.example.java.android1.movie_search.utils.mapActorUIToLocalActorData
import com.example.java.android1.movie_search.utils.mapLocalActorDataToActorUI

/**
 * Implementation of the interface for getting data from Remote Server
 */

class MovieActorRepositoryImpl(
    private val remoteDataSource: ActorDataSource<ActorDTO>,
    private val localDataSource: LocalActorDataSource
) : MovieActorRepository {

    /**
     * Method for getting detailed information about the actor
     * @param personId - Id of the actor to request information for
     * @param language - Response language
     */


    override suspend fun getMovieActorDetails(
        personId: Long,
        language: String,
        isNetworkAvailable: Boolean,
    ): ActorState {
        return if (isNetworkAvailable) {
            val actor = mapActorDtoToActorUI(
                remoteDataSource.getMovieActorDetails(
                    personId = personId,
                    language = language
                )
            )
            localDataSource.saveActorData(mapActorUIToLocalActorData(actor))
            ActorState.Success(listOf(actor))
        } else {
            ActorState.Success(
                listOf(
                    mapLocalActorDataToActorUI(
                        localDataSource.getMovieActorDetails(
                            personId = personId,
                            language = language
                        )
                    )
                )
            )
        }
    }
}