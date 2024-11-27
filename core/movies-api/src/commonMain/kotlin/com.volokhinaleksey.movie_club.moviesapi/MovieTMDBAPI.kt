package com.volokhinaleksey.movie_club.moviesapi

import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.model.remote.GenresDTO
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB

/**
 * The interface for interacting with TMDB API.
 */

interface MovieTMDBAPI {

    /**
     * The method for getting a details of the movie
     */

    suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        extraRequests: String,
    ): MovieDataTMDB

    /**
     * The method for getting a movie by search
     */

    suspend fun getMoviesBySearch(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
    ): CategoryMoviesTMDB

    /**
     * The method for getting the actor data
     */

    suspend fun getActorData(
        personId: Long?,
        language: String,
    ): ActorDTO

    /**
     * The method for getting the category of movies
     */

    suspend fun getMoviesByCategory(
        categoryId: String,
        language: String,
        page: Int = 1,
    ): CategoryMoviesTMDB

    suspend fun getUpcomingMovies(
        startReleaseDate: String,
        language: String,
        page: Int = 1,
        sortBy: String = "popularity.desc",
    ): CategoryMoviesTMDB

    /**
     * The method for getting the similar movies
     */

    suspend fun getSimilarMovies(
        movieId: Int,
        language: String,
        page: Int,
    ): CategoryMoviesTMDB

    suspend fun getGenres(
        language: String,
    ): GenresDTO

}