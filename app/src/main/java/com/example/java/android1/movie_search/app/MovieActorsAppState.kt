package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.ActorDTO

sealed class MovieActorsAppState {
    data class Success(val data: ActorDTO) : MovieActorsAppState()
    data class Error(val error: Throwable) : MovieActorsAppState()
    object Loading : MovieActorsAppState()
}