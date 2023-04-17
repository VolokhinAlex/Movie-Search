package com.example.java.android1.movie_search.datasource.details

import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalDetailsDataSource : DetailsDataSource<LocalMovieData> {

    /**
     * Method for saving a movie to a local database
     * @param localMovieData - The movie to save
     */

    suspend fun saveMovieDetailsToLocalDataBase(localMovieData: LocalMovieData)

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)

    suspend fun saveSimilarMovies(localMovieData: LocalMovieData, movieId: Int)

    suspend fun saveMovie(localMovieData: LocalMovieData)
}