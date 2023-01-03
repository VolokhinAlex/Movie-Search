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

    fun getMoviesFromSearch(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        val request = movieApi.getMoviesFromSearch(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page,
            adult = adult,
            query = query
        )
        request.enqueue(callback)
    }

    fun getPopularMovies(language: String, page: Int, callback: Callback<CategoryMoviesTMDB>) {
        val request = movieApi.getPopularMovies(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
        request.enqueue(callback)
    }

    fun getNowPlayingMovies(language: String, page: Int, callback: Callback<CategoryMoviesTMDB>) {
        val request = movieApi.getNowPlayingMovies(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
        request.enqueue(callback)
    }

    fun getTopRatedMovies(language: String, page: Int, callback: Callback<CategoryMoviesTMDB>) {
        val request = movieApi.getTopRatedMovies(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
        request.enqueue(callback)
    }

    fun getUpcomingMovies(language: String, page: Int, callback: Callback<CategoryMoviesTMDB>) {
        val request = movieApi.getUpcomingMovies(
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
        request.enqueue(callback)
    }

    fun getMoviesCategoryForCompose(
        category: String,
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        val request = movieApi.getMoviesCategoryForCompose(
            category = category,
            token = BuildConfig.MOVIE_API_KEY,
            language = language,
            page = page
        )
        request.enqueue(callback)
    }

    fun getActorData(personId: Long, language: String, callback: Callback<ActorDTO>) {
        val request = movieApi.getActorData(
            personId = personId,
            token = BuildConfig.MOVIE_API_KEY,
            language = language
        )
        request.enqueue(callback)
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