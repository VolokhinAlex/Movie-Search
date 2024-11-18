package com.volokhinaleksey.movie_club.moviesapi

import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.model.remote.GenresDTO
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.movies_api.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The interface for interacting with TMDB API.
 */

interface MovieTMDBAPI {

    /**
     * The method for getting a details of the movie
     */

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String,
        @Query("append_to_response", encoded = true) extraRequests: String
    ): MovieDataTMDB

    /**
     * The method for getting a movie by search
     */

    @GET("3/search/movie")
    suspend fun getMoviesBySearch(
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("query") query: String
    ): CategoryMoviesTMDB

    /**
     * The method for getting the actor data
     */

    @GET("3/person/{person_id}")
    suspend fun getActorData(
        @Path("person_id") personId: Long?,
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String
    ): ActorDTO

    /**
     * The method for getting the category of movies
     */

    @GET("3/movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category") categoryId: String,
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): CategoryMoviesTMDB

    @GET("3/discover/movie")
    suspend fun getUpcomingMovies(
        @Query("primary_release_date.gte") startReleaseDate: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): CategoryMoviesTMDB

    /**
     * The method for getting the similar movies
     */

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String,
        @Query("page") page: Int
    ): CategoryMoviesTMDB

    @GET("3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") token: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String
    ): GenresDTO

}