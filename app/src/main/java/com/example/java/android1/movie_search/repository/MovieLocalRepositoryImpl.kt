package com.example.java.android1.movie_search.repository

import androidx.annotation.WorkerThread
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieDao
import com.example.java.android1.movie_search.utils.convertMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.convertMovieEntityToListMovieDataRoom
import com.example.java.android1.movie_search.utils.convertMovieEntityToMovieDataRoom

/**
 * The implementation Movie Local Repository for interacting with local database
 */

class MovieLocalRepositoryImpl(
    private val localDataRoom: MovieDao
) : MovieLocalRepository {

    override fun getAllMoviesFromLocalDataBase(): List<MovieDataRoom> {
        return convertMovieEntityToListMovieDataRoom(localDataRoom.all())
    }

    override fun getAllFavoritesFromLocalDataBase(): List<MovieDataRoom> {
        return convertMovieEntityToListMovieDataRoom(localDataRoom.getAllFavorites())
    }

    override fun getMovieFromLocalDataBase(movieId: Int?): MovieDataRoom =
        convertMovieEntityToMovieDataRoom(movieId?.let { localDataRoom.getMovieByMovieId(it) })

    @WorkerThread
    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.insert(convertMovieDtoToMovieEntity(movieDataTMDB))
    }

    @WorkerThread
    override suspend fun updateMovieNoteInLocalDataBase(movieId: Int, note: String) {
        localDataRoom.updateNote(movieId, note)
    }

    @WorkerThread
    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        localDataRoom.updateFavorite(movieId, favorite)
    }

}