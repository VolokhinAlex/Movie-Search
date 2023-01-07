package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import retrofit2.Call
import retrofit2.Response
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
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("append_to_response", encoded = true) extraRequests: String
    ): Call<MovieDataTMDB>

    /**
     * The method for getting a movie by name
     */

    @GET("3/search/movie")
    fun getMoviesFromSearch(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("query") query: String
    ): Call<CategoryMoviesTMDB>

    @GET("3/search/movie")
    suspend fun getMoviesFromSearchPagination(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("query") query: String
    ): Response<CategoryMoviesTMDB>

    /**
     * The method for getting the category of movies
     */

    @GET("3/movie/{category}")
    fun getMoviesCategoryForCompose(
        @Path("category") category: String,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<CategoryMoviesTMDB>

    /**
     * The method for getting the actor data
     */

    @GET("3/person/{person_id}")
    fun getActorData(
        @Path("person_id") personId: Long?,
        @Query("api_key") token: String,
        @Query("language") language: String
    ): Call<ActorDTO>

    @GET("3/movie/{category}")
    suspend fun getMoviesCategory(
        @Path("category") category: String,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<CategoryMoviesTMDB>

}