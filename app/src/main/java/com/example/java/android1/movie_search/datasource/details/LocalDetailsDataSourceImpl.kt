package com.example.java.android1.movie_search.datasource.details

import com.example.java.android1.movie_search.model.local.ActorEntity
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.model.local.TrailerEntity
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapMovieEntityToLocalMovieData

class LocalDetailsDataSourceImpl(
    private val localDataRoom: MoviesDataBase
) : LocalDetailsDataSource {

    /**
     * Method for requesting a movie by id from a local database
     * @param movieId - Movie Id to get a movie
     */

    override suspend fun getMovieFromLocalDataBase(movieId: Int): LocalMovieData {
        return mapMovieEntityToLocalMovieData(
            movieEntity = localDataRoom.moviesDao().getMovieByMovieId(movieId),
            trailers = localDataRoom.trailerDao().getTrailerById(movieId = movieId),
            actors = localDataRoom.actorDao().getActorsByMovieId(movieId = movieId)
        )
    }

    /**
     * Method for saving a movie to a local database
     * @param movieDataTMDB - The movie to save
     */

    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.moviesDao().updateRuntimeAndGenres(
            movieId = movieDataTMDB.id ?: 0,
            genres = movieDataTMDB.genres?.joinToString { it.name.orEmpty() }.orEmpty(),
            runtime = movieDataTMDB.runtime ?: 0
        )
        localDataRoom.trailerDao().insert(movieDataTMDB.videos?.results?.map {
            TrailerEntity(
                id = movieDataTMDB.id.toString(),
                name = it.name.orEmpty(),
                key = it.key,
                type = it.type
            )
        } ?: emptyList())
        val actors = movieDataTMDB.credits?.cast?.map {
            ActorEntity(
                actorId = it.id ?: 0,
                id = movieDataTMDB.id?.toLong(),
                name = it.name,
                character = it.character,
                biography = null,
                birthday = null,
                imdbId = null,
                placeOfBirth = null,
                popularity = null,
                profilePath = it.profile_path
            )
        } ?: emptyList()
        localDataRoom.actorDao().insert(actors)
    }

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        localDataRoom.moviesDao().updateFavorite(movieId = movieId, favorite = favorite)
    }

}