package com.volokhinaleksey.movie_club.datasource.actor

import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.network.ApiHolder

class RemoteActorDataSource(
    private val apiHolder: ApiHolder
) : ActorDataSource<ActorDTO> {

    override suspend fun getMovieActorDetails(personId: Long, language: String): ActorDTO {
        return apiHolder.moviesApi.getActorData(personId = personId, language = language)
    }

}