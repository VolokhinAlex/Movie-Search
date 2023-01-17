package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.app.RoomAppState.*
import com.example.java.android1.movie_search.model.MovieDataRoom

/**
 * States that come from the local ROOM database. Total 3 states
 * 1. [Success] - If the request is successful, stores a list of movies.
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class RoomAppState {
    data class Success(val moviesData: List<MovieDataRoom>) : RoomAppState()
    data class Error(val errorMessage: Throwable) : RoomAppState()
    object Loading : RoomAppState()
}
