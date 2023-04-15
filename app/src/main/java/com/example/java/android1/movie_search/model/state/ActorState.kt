package com.example.java.android1.movie_search.model.state

import com.example.java.android1.movie_search.model.ui.ActorUI

sealed interface ActorState {
    data class Success(val data: List<ActorUI>) : ActorState

    data class Error(val errorMessage: Throwable) : ActorState

    object Loading : ActorState
}