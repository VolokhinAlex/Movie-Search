package com.volokhinaleksey.movie_club.datasource.actor

import com.volokhinaleksey.movie_club.model.local.LocalActorData

interface LocalActorDataSource : ActorDataSource<LocalActorData> {

    suspend fun saveActorData(localActorData: LocalActorData)

    suspend fun getActorsByMovieId(movieId: Long): List<LocalActorData>
}