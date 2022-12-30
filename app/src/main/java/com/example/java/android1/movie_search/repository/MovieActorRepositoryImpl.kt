package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import retrofit2.Callback

class MovieActorRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieActorRepository {

    override fun getMovieActor(personId: Long, language: String, callback: Callback<ActorDTO>) {
        remoteDataSource.getActorData(
            personId = personId,
            language = language,
            callback = callback
        )
    }

}