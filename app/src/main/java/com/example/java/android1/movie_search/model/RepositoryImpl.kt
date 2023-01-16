package com.example.java.android1.movie_search.model

class RepositoryImpl : Repository {

    override fun getMovieFromServer() = getListOfMoviesFromLocalStorage()

    override fun getMovieFromLocalStorage() = getListOfMoviesFromLocalStorage()

}