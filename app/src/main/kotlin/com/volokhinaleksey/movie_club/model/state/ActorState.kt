package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.ActorUI

sealed interface ActorState {
    data class Success(val data: List<ActorUI>) : ActorState

    data class Error(val errorMessage: Throwable) : ActorState

    object Loading : ActorState
}