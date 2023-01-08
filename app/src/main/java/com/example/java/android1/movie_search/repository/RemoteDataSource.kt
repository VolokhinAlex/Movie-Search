package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.BuildConfig
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val movieApi: MovieTMDBAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(MovieInterceptor()))
        .build().create(MovieTMDBAPI::class.java)

    /**
     * A method for getting details of the movie from a remote server
     */

    fun getMovieDetails(movieId: Int, language: String, callback: Callback<MovieDataTMDB>) {
        val request =
            movieApi.getMovieDetails(
                movieId = movieId,
                token = BuildConfig.MOVIE_API_KEY,
                language = language,
                extraRequests = "credits,videos"
            )
        request.enqueue(callback)
    }

    /**
     * A method for getting a actor data from a remote server
     */

    fun getActorData(personId: Long, language: String, callback: Callback<ActorDTO>) {
        val request = movieApi.getActorData(
            personId = personId,
            token = BuildConfig.MOVIE_API_KEY,
            language = language
        )
        request.enqueue(callback)
    }

    /**
     * A method for getting a category movies from a remote server
     */

    suspend fun getCategoryMovies(
        category: String,
        language: String,
        page: Int,
    ): retrofit2.Response<CategoryMoviesTMDB> {
        return movieApi.getCategoryMovies(
            category = category,
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
    }

    /**
     * A method for getting a list of movies by search from a remote server
     */

    suspend fun getMoviesBySearch(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
    ): retrofit2.Response<CategoryMoviesTMDB> {
        return movieApi.getMoviesBySearch(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page,
            adult = adult,
            query = query
        )
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
        client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return client.build()
    }

    class MovieInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }
}