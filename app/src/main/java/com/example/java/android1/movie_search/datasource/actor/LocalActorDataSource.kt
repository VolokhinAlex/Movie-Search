package com.example.java.android1.movie_search.datasource.actor

import com.example.java.android1.movie_search.model.local.LocalActorData

interface LocalActorDataSource : ActorDataSource<LocalActorData> {

    suspend fun saveActorData(localActorData: LocalActorData)

    suspend fun getActorsByMovieId(movieId: Long): List<LocalActorData>
}