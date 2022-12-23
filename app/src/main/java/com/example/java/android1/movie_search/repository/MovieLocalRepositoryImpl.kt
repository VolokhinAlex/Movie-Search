package com.example.java.android1.movie_search.repository

import androidx.annotation.WorkerThread
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieDao
import com.example.java.android1.movie_search.room.MovieEntity
import com.example.java.android1.movie_search.utils.converterMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.converterMovieEntityToMovieDataRoom
import kotlinx.coroutines.flow.Flow

class MovieLocalRepositoryImpl(
    private val localDataRoom: MovieDao
) : MovieLocalRepository {

    override fun all(): Flow<List<MovieEntity>> = localDataRoom.all()

    override fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom =
        converterMovieEntityToMovieDataRoom(localDataRoom.getMovieByMovieId(movieId))

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