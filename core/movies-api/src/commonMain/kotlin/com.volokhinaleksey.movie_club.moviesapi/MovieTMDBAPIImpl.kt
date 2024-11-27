package com.volokhinaleksey.movie_club.moviesapi

import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.model.remote.GenresDTO
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class MovieTMDBAPIImpl(
    private val ktorClient: HttpClient,
) : MovieTMDBAPI {
    override suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        extraRequests: String,
    ): MovieDataTMDB = ktorClient.get {
        url("movie/$movieId")
        parameter("language", language)
        parameter("append_to_response", extraRequests)
    }.body()

    override suspend fun getMoviesBySearch(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
    ): CategoryMoviesTMDB = ktorClient.get {
        url("search/movie")
        parameter("language", language)
        parameter("page", page)
        parameter("include_adult", adult)
        parameter("query", query)
    }.body()

    override suspend fun getActorData(
        personId: Long?,
        language: String,
    ): ActorDTO = ktorClient.get {
        url("person/$personId")
        parameter("language", language)
    }.body()

    override suspend fun getMoviesByCategory(
        categoryId: String,
        language: String,
        page: Int,
    ): CategoryMoviesTMDB = ktorClient.get {
        url("movie/$categoryId")
        parameter("language", language)
        parameter("page", page)
    }.body()

    override suspend fun getUpcomingMovies(
        startReleaseDate: String,
        language: String,
        page: Int,
        sortBy: String,
    ): CategoryMoviesTMDB = ktorClient.get {
        url("discover/movie")
        parameter("language", language)
        parameter("primary_release_date", startReleaseDate)
        parameter("page", page)
        parameter("sort_by", sortBy)
    }.body()

    override suspend fun getSimilarMovies(
        movieId: Int,
        language: String,
        page: Int,
    ): CategoryMoviesTMDB = ktorClient.get {
        url("movie/$movieId/similar")
        parameter("language", language)
        parameter("page", page)
    }.body()

    override suspend fun getGenres(
        language: String
    ): GenresDTO = ktorClient.get {
        url("genre/movie/list")
        parameter("language", language)
    }.body()

}