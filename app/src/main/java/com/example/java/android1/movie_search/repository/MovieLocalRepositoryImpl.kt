package com.example.java.android1.movie_search.repository

import androidx.lifecycle.LiveData
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieDao
import com.example.java.android1.movie_search.room.MovieEntity
import com.example.java.android1.movie_search.utils.converterMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.converterMovieEntityToMovieDataRoom

class MovieLocalRepositoryImpl(
    private val localDataRoom: MovieDao
) : MovieLocalRepository {

    override fun all(): LiveData<List<MovieEntity>> {
        return localDataRoom.all()
    }

    override fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom {
        return converterMovieEntityToMovieDataRoom(localDataRoom.getMovieByMovieId(movieId))
    }

    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.insert(converterMovieDtoToMovieEntity(movieDataTMDB))
    }

    override suspend fun updateMovieNoteInLocalDataBase(movieId: Int, note: String) {
        localDataRoom.updateNote(movieId, note)
    }

    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        localDataRoom.updateFavorite(movieId, favorite)
    }

}