package com.volokhinaleksey.movie_club.moviesapi.di

import com.volokhinaleksey.movie_club.movies_api.BuildConfig
import com.volokhinaleksey.movie_club.moviesapi.ApiConfig
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import com.volokhinaleksey.movie_club.moviesapi.MovieTMBDCore
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPI
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPIImpl
import com.volokhinaleksey.movie_club.utils.TMDB_API_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val network = module {
    single<ApiConfig> {
        ApiConfig(
            baseUrl = TMDB_API_BASE_URL,
            authToken = BuildConfig.MOVIE_API_KEY
        )
    }

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(DefaultRequest) {
                url(get<ApiConfig>().baseUrl)
                url.parameters.append("api_key", BuildConfig.MOVIE_API_KEY)
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    single<MovieTMDBAPI> { MovieTMDBAPIImpl(get()) }

    single<CoreApi> { MovieTMBDCore(get()) }
}