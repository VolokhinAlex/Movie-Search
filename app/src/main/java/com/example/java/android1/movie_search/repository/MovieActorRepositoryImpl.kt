package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from TMDB API
 */

class MovieActorRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieActorRepository {

    override fun getMovieActorFromRemoteServer(personId: Long, language: String, callback: Callback<ActorDTO>) {
        remoteDataSource.getActorData(
            personId = personId,
            language = language,
            callback = callback
        )
    }

}