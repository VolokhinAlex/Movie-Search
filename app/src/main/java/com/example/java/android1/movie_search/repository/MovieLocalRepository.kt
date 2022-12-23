package com.example.java.android1.movie_search.repository

import androidx.lifecycle.LiveData
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieEntity

interface MovieLocalRepository {

    fun all(): LiveData<List<MovieEntity>>

    fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom

    suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB)

    suspend fun updateMovieNoteInLocalDataBase(movieId: Int, note: String)

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)
}