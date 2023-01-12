package com.example.java.android1.movie_search.model

interface Repository {

    fun getMovieFromServer(): List<MovieData>
    fun getMovieFromLocalStorage(): List<MovieData>

}