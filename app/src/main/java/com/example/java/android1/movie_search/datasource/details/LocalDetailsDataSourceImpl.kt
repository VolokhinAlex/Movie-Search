package com.example.java.android1.movie_search.datasource.details

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.convertMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.convertMovieEntityToMovieDataRoom

class LocalDetailsDataSourceImpl(
    private val localDataRoom: MoviesDataBase
) : LocalDetailsDataSource {

    /**
     * Method for requesting a movie by id from a local database
     * @param movieId - Movie Id to get a movie
     */

    override suspend fun getMovieFromLocalDataBase(movieId: Int): MovieDataRoom {
        return convertMovieEntityToMovieDataRoom(
            entity = localDataRoom.moviesDao().getMovieByMovieId(movieId)
        )
    }

    /**
     * Method for saving a movie to a local database
     * @param movieDataTMDB - The movie to save
     */

    override suspend fun saveMovieToLocalDataBase(movieDataTMDB: MovieDataTMDB) {
        localDataRoom.moviesDao().insert(entity = convertMovieDtoToMovieEntity(movieDataTMDB))
    }

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        localDataRoom.moviesDao().updateFavorite(movieId = movieId, favorite = favorite)
    }

}