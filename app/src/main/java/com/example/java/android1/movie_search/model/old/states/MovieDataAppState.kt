package com.example.java.android1.movie_search.model.old.states

import com.example.java.android1.movie_search.model.old.states.MovieDataAppState.*
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB

/**
 * States that come from the remote server. Total 3 states
 * 1. [Success] - If the request is successful, stores a list of movies.
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */


sealed class MovieDataAppState {
    data class Success(val movieDetailsData: MovieDataTMDB) : MovieDataAppState()
    data class Error(val errorMessage: Throwable) : MovieDataAppState()
    object Loading : MovieDataAppState()
}