package com.volokhinaleksey.movie_club.moviesapi.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun httpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}