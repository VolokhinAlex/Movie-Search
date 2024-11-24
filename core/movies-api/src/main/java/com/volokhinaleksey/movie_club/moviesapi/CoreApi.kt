package com.volokhinaleksey.movie_club.moviesapi

interface CoreApi {
    val moviesApi: MovieTMDBAPI
}
class MovieTMBDCore(override val moviesApi: MovieTMDBAPI) : CoreApi