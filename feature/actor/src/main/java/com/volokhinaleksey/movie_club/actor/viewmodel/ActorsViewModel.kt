package com.volokhinaleksey.movie_club.actor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.domain.ActorInteractor
import com.volokhinaleksey.movie_club.domain.LocaleInteractor
import com.volokhinaleksey.movie_club.model.state.ActorDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorsViewModel(
    private val actorInteractor: ActorInteractor,
    private val localeInteractor: LocaleInteractor,
) : ViewModel() {

    private val _actorDetailsState = MutableStateFlow<ActorDetailsState>(ActorDetailsState.Loading)
    val actorDetailsState get() = _actorDetailsState.asStateFlow()

    fun getActorDetails(actorId: Long) {
        viewModelScope.launch {
            actorInteractor.syncActorDetails(
                actorId = actorId,
                lang = localeInteractor.getCurrentLanguage()
            )
            _actorDetailsState.value = actorInteractor.getActorDetails(actorId = actorId)
        }
    }

}