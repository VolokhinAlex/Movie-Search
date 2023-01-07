package com.example.java.android1.movie_search.app

import com.example.java.android1.movie_search.model.MovieDataRoom

sealed class RoomAppState {
    data class Success(val data: List<MovieDataRoom>) : RoomAppState()
    data class Error(val error: Throwable) : RoomAppState()
    object Loading : RoomAppState()
}
