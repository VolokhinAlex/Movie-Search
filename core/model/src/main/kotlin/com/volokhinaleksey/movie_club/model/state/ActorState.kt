package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.Actor

sealed interface ActorState {
    data class Success(val data: List<Actor>) : ActorState
    data class Error(val errorMessage: Throwable) : ActorState
    data object Loading : ActorState
}