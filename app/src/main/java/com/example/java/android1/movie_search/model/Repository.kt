package com.example.java.android1.movie_search.model

interface Repository {

    fun getMovieFromServer(): MovieData
    fun getMovieFromLocalStorage(): MovieData

}