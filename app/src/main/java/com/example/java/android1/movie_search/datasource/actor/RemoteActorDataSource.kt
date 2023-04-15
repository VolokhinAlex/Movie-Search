package com.example.java.android1.movie_search.datasource.actor

import com.example.java.android1.movie_search.model.old.remote.ActorDTO
import com.example.java.android1.movie_search.network.ApiHolder

class RemoteActorDataSource(
    private val apiHolder: ApiHolder
) : ActorDataSource<ActorDTO> {

    override suspend fun getMovieActorDetails(personId: Long, language: String): ActorDTO {
        return apiHolder.moviesApi.getActorData(personId = personId, language = language)
    }

}