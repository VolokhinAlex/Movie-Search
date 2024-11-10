package com.volokhinaleksey.movie_club.datasource.actor

import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.moviesapi.CoreApi

class RemoteActorDataSource(
    private val coreApi: CoreApi
) : ActorDataSource<ActorDTO> {

    override suspend fun getMovieActorDetails(personId: Long, language: String): ActorDTO {
        return coreApi.moviesApi.getActorData(personId = personId, language = language)
    }

}