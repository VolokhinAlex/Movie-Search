package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.app.MovieActorAppState.*
import com.example.java.android1.movie_search.model.ActorDTO

/**
 * States that come from the remote server. Total 3 states
 * 1. [Success] - If the request is successful, stores a actor data.
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class MovieActorAppState {
    data class Success(val data: ActorDTO) : MovieActorAppState()
    data class Error(val error: Throwable) : MovieActorAppState()
    object Loading : MovieActorAppState()
}