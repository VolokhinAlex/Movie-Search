package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.model.ui.MovieUI

interface DetailsDatabaseRepository : DetailsRepository {

    /**
     * Method for saving a movie to a local database
     * @param localMovieData - The movie to save
     */

    suspend fun saveMovieDetailsToLocalDataBase(movieUI: MovieUI)

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)

    suspend fun saveSimilarMovies(movieUI: MovieUI, movieId: Int)

    suspend fun saveMovie(movieUI: MovieUI)
}