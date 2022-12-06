package com.example.java.android1.movie_search.model

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): List<MovieData> {
        return getListOfMoviesFromLocalStorage()
    }

    override fun getMovieFromLocalStorage() = getListOfMoviesFromLocalStorage()

}