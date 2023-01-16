package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.ActorDTO

sealed class MovieActorState {
    data class Success(val data: ActorDTO) : MovieActorState()
    data class Error(val error: Throwable) : MovieActorState()
    object Loading : MovieActorState()
}