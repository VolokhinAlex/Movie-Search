package com.example.java.android1.movie_search.model

interface Repository {

    fun getMovieFromServer(): List<MovieDataTMDB>
    fun getMovieFromLocalStorage(): List<MovieDataTMDB>
    fun loadData()

}