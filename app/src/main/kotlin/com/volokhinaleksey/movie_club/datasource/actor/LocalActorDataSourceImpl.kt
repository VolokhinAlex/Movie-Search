package com.volokhinaleksey.movie_club.datasource.actor

import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.utils.mapActorEntityToLocalActorData
import com.volokhinaleksey.movie_club.utils.mapLocalActorDataToActorEntity

class LocalActorDataSourceImpl(
    private val db: MoviesDataBase
) : LocalActorDataSource {
    override suspend fun saveActorData(localActorData: LocalActorData) {
        db.actorDao().insert(mapLocalActorDataToActorEntity(localActorData))
    }

    override suspend fun getActorsByMovieId(movieId: Long): List<LocalActorData> {
        return db.actorDao().getActorsByMovieId(movieId = movieId.toInt()).map {
            mapActorEntityToLocalActorData(it)
        }
    }

    override suspend fun getMovieActorDetails(personId: Long, language: String): LocalActorData {
        return mapActorEntityToLocalActorData(
            db.actorDao().getActorById(actorId = personId.toInt())
        )
    }

}