package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Actor
import com.volokhinaleksey.movie_club.moviesapi.CoreApi

interface ActorRepository {
    suspend fun getActorDetails(actorId: Long): Actor
    suspend fun syncActorDetails(actorId: Long, lang: String)
}

class ActorRepositoryImpl(
    private val dataBase: MovieDataBase,
    private val coreApi: CoreApi,
) : ActorRepository {
    override suspend fun getActorDetails(actorId: Long): Actor {
        return dataBase.actorsDao().getActorById(actorId)?.asExternalModel() ?: Actor()
    }

    override suspend fun syncActorDetails(actorId: Long, lang: String) {
        coreApi.moviesApi.getActorData(personId = actorId, language = lang)
            .also {
                dataBase.actorsDao().update(
                    actorId = actorId,
                    biography = it.biography.orEmpty(),
                    birthday = it.birthday.orEmpty()
                )
            }
    }
}