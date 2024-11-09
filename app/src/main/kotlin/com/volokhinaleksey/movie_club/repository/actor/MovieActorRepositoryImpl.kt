package com.volokhinaleksey.movie_club.repository.actor

import com.volokhinaleksey.movie_club.datasource.actor.ActorDataSource
import com.volokhinaleksey.movie_club.datasource.actor.LocalActorDataSource
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.state.ActorState
import com.volokhinaleksey.movie_club.utils.mapActorDtoToActorUI
import com.volokhinaleksey.movie_club.utils.mapActorUIToLocalActorData
import com.volokhinaleksey.movie_club.utils.mapLocalActorDataToActorUI

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