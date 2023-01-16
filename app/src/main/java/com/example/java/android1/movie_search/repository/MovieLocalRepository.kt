package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB

interface MovieLocalRepository {

    fun getAllMovies(): List<MovieDataRoom>

    fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom

    suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB)

    suspend fun updateMovieNoteInLocalDataBase(movieId: Int, note: String)

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)
}