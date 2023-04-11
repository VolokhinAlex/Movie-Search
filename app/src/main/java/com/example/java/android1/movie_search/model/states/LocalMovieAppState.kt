package com.example.java.android1.movie_search.model.states

import com.example.java.android1.movie_search.model.states.LocalMovieAppState.*
import com.example.java.android1.movie_search.model.MovieDataRoom

/**
 * States that come from the local ROOM database. Total 3 states
 * 1. [Success] - If the request is successful, stores a list of movies.
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class LocalMovieAppState {
    data class Success(val moviesData: List<MovieDataRoom>) : LocalMovieAppState()
    data class Error(val errorMessage: Throwable) : LocalMovieAppState()
    object Loading : LocalMovieAppState()
}