package com.volokhinaleksey.movie_club.datasource.actor

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.model.local.LocalActorData

class LocalActorDataSourceImpl(
    private val db: MovieDataBase
) : LocalActorDataSource {
    override suspend fun saveActorData(localActorData: LocalActorData) {
        TODO()
    }

    override suspend fun getActorsByMovieId(movieId: Long): List<LocalActorData> {
        TODO()
    }

    override suspend fun getMovieActorDetails(personId: Long, language: String): LocalActorData {
        TODO()
    }

}