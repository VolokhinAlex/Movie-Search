package com.volokhinaleksey.movie_club.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.model.state.ActorState
import com.volokhinaleksey.movie_club.repository.actor.MovieActorRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieActorViewModel(
    private val movieActorRepository: MovieActorRepository
) : ViewModel() {

    private val _movieActorData: MutableLiveData<ActorState> = MutableLiveData()
    val movieActorData: LiveData<ActorState> = _movieActorData

    /**
     * The method for getting detailed information about an actor
     * @param personId - ID to search for an actor
     * @param language - Language to display translated data for the fields that support it.
     */

    fun getMovieActorData(personId: Long, language: String, isOnline: Boolean) {
        _movieActorData.value = ActorState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _movieActorData.postValue(ActorState.Error(error))
        }) {
            _movieActorData.postValue(
                movieActorRepository.getMovieActorDetails(
                    personId = personId,
                    language = language,
                    isNetworkAvailable = isOnline
                )
            )
        }
    }

}
