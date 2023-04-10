package com.example.java.android1.movie_search.datasource.details

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB

interface LocalDetailsDataSource {

    /**
     * Method for requesting a movie by id from a local database
     * @param movieId - Movie Id to get a movie
     */

    suspend fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom

    /**
     * Method for saving a movie to a local database
     * @param movieDataTMDB - The movie to save
     */

    suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB)

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)

}