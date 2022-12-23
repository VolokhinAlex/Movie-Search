package com.example.java.android1.movie_search.viewmodel

import com.example.java.android1.movie_search.model.MovieDataRoom

sealed class RoomAppState {
    data class Success(val data: MovieDataRoom) : RoomAppState()
    data class Error(val error: Throwable) : RoomAppState()
    object Loading : RoomAppState()
}
