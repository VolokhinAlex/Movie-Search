package com.example.java.android1.movie_search.repository

import androidx.annotation.WorkerThread
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieDao
import com.example.java.android1.movie_search.utils.convertMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.convertMovieEntityToListMovieDataRoom
import com.example.java.android1.movie_search.utils.convertMovieEntityToMovieDataRoom

/**
 * The implementation Movie Local Repository for interacting with local database
 */

class MovieLocalRepositoryImpl(
    private val localDataRoom: MovieDao
) : MovieLocalRepository {

    /**
     * A method for getting all your favorite movies
     */

    override fun getAllFavoritesFromLocalDataBase(): List<MovieDataRoom> {
        return convertMovieEntityToListMovieDataRoom(entity = localDataRoom.getAllFavorites())
    }

    /**
     * Method for requesting a movie by id from a local database
     * @param movieId - Movie Id to get a movie
     */

    override fun getMovieFromLocalDataBase(movieId: Int?): MovieDataRoom =
        convertMovieEntityToMovieDataRoom(entity = movieId?.let { localDataRoom.getMovieByMovieId(it) })

    /**
     * Method for saving a movie to a local database
     * @param movieDataTMDB - The movie to save
     */

    @WorkerThread
    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.insert(entity = convertMovieDtoToMovieEntity(movieDataTMDB))
    }

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    @WorkerThread
    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        localDataRoom.updateFavorite(movieId = movieId, favorite = favorite)
    }

}