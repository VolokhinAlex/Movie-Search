package com.example.java.android1.movie_search.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieDao
import com.example.java.android1.movie_search.room.MovieEntity
import com.example.java.android1.movie_search.utils.converterMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.converterMovieEntityToListMovieDataRoom
import com.example.java.android1.movie_search.utils.converterMovieEntityToMovieDataRoom
import kotlinx.coroutines.flow.Flow

/**
 * The implementation Movie Local Repository for interacting with local database
 */

class MovieLocalRepositoryImpl(
    private val localDataRoom: MovieDao
) : MovieLocalRepository {

    override fun getAllMovies(): List<MovieDataRoom> {
        return converterMovieEntityToListMovieDataRoom(localDataRoom.all())
    }

    override fun getAllFavorites(): List<MovieDataRoom> {
        return converterMovieEntityToListMovieDataRoom(localDataRoom.getAllFavorites())
    }

    override fun getMovieFromLocalDataBase(movieId: Int?): MovieDataRoom =
        converterMovieEntityToMovieDataRoom(movieId?.let { localDataRoom.getMovieByMovieId(it) })

    @WorkerThread
    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.insert(converterMovieDtoToMovieEntity(movieDataTMDB))
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