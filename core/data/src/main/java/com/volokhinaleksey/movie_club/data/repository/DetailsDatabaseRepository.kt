package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.model.ui.Movie

interface DetailsDatabaseRepository : DetailsRepository {

    /**
     * Method for saving a movie to a local database
     * @param localMovieData - The movie to save
     */

    suspend fun saveMovieDetailsToLocalDataBase(movie: Movie)

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean)

    suspend fun saveSimilarMovies(movie: Movie, movieId: Int)

    suspend fun saveMovie(movie: Movie)
}