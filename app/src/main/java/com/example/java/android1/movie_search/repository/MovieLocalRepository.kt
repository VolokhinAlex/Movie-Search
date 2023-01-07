package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB

/**
 * The local repository for interacting with movie local database
 */

interface MovieLocalRepository {
    fun getAllMoviesFromLocalDataBase(): List<MovieDataRoom>

    fun getAllFavoritesFromLocalDataBase(): List<MovieDataRoom>

    fun getMovieFromLocalDataBase(movieId: Int?): MovieDataRoom

    suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB)

    suspend fun updateMovieNoteInLocalDataBase(movieId: Int, note: String)

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)
}