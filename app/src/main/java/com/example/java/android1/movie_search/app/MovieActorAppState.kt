package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.ActorDTO

sealed class MovieActorAppState {
    data class Success(val data: ActorDTO) : MovieActorAppState()
    data class Error(val error: Throwable) : MovieActorAppState()
    object Loading : MovieActorAppState()
}