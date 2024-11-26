package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.Actor

sealed interface ActorDetailsState {
    data object Loading : ActorDetailsState
    data class Success(val details: Actor) : ActorDetailsState
    data class Error(val msg: String) : ActorDetailsState
}