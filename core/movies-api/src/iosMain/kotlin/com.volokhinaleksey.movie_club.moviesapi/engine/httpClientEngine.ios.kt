package com.volokhinaleksey.movie_club.moviesapi.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun httpClientEngine(): HttpClientEngine {
    return Darwin.create()
}