package com.volokhinaleksey.movie_club.moviesapi.di

import com.volokhinaleksey.movie_club.moviesapi.ApiConfig
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import com.volokhinaleksey.movie_club.moviesapi.MovieTMBDCore
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPI
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPIImpl
import com.volokhinaleksey.movie_club.moviesapi.engine.httpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import iosApp.core.movies_api.BuildConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal const val TMDB_API_BASE_URL = "https://api.themoviedb.org/3/"

val network = module {
    single<ApiConfig> {
        ApiConfig(
            baseUrl = TMDB_API_BASE_URL,
            authToken = BuildConfig.MOVIE_API_KEY
        )
    }

    single {
        createHttpClient(httpClientEngine(), get<ApiConfig>())
    }

    single<MovieTMDBAPI> { MovieTMDBAPIImpl(get()) }

    single<CoreApi> { MovieTMBDCore(get()) }
}

fun createHttpClient(engine: HttpClientEngine, apiConfig: ApiConfig): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        install(DefaultRequest) {
            url(apiConfig.baseUrl)
            url.parameters.append("api_key", apiConfig.authToken)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}