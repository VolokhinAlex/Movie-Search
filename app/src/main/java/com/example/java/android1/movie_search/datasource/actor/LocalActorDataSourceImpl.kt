package com.example.java.android1.movie_search.datasource.actor

import com.example.java.android1.movie_search.model.local.LocalActorData
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapActorEntityToLocalActorData
import com.example.java.android1.movie_search.utils.mapLocalActorDataToActorEntity

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