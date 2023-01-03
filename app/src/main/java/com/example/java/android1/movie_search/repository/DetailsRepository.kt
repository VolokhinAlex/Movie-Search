package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.MovieDataTMDB
import retrofit2.Callback

/**
 * The remote repository to get the details data of movie from TMDB API
 */

interface DetailsRepository {
    fun getMovieDetailsFromRemoteServer(movieId: Int, language: String, callback: Callback<MovieDataTMDB>)
}