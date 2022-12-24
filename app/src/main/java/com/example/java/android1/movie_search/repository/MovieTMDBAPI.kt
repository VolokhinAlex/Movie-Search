package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTMDBAPI {

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("append_to_response") actors: String
    ) : Call<MovieDataTMDB>

    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<CategoryMoviesTMDB>

    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<CategoryMoviesTMDB>

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<CategoryMoviesTMDB>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<CategoryMoviesTMDB>

    @GET("3/search/movie")
    fun getMoviesFromSearch(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean,
        @Query("query") query: String
    ) : Call<CategoryMoviesTMDB>

}