package com.example.java.android1.movie_search.model

class RepositoryImpl : Repository {

    private var dataTMDB: List<MovieDataTMDB> = listOf()
    private val listener = object : ParseMoviesFromTMDB.MovieDataLoadListener {

        override fun onLoaded(movieData: List<MovieDataTMDB>) {
            dataTMDB = movieData
        }

        override fun onFailed(e: Throwable) {
        }

    }
    private val parse = ParseMoviesFromTMDB(listener, "ru-RU", "popular")

    override fun getMovieFromServer() = dataTMDB

    override fun getMovieFromLocalStorage() = dataTMDB

    override fun loadData() {
        parse.parseData()
    }
}
