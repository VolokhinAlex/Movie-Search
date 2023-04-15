package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.movie_search.model.states.MovieActorAppState
import com.example.java.android1.movie_search.repository.actor.MovieActorRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieActorViewModel(
    private val movieActorRepository: MovieActorRepository
) : ViewModel() {

    private val _movieActorData: MutableLiveData<MovieActorAppState> = MutableLiveData()
    val movieActorData: LiveData<MovieActorAppState> = _movieActorData

    /**
     * The method for getting detailed information about an actor
     * @param personId - ID to search for an actor
     * @param language - Language to display translated data for the fields that support it.
     */

    fun getMovieActorData(personId: Long, language: String) {
        _movieActorData.value = MovieActorAppState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _movieActorData.postValue(MovieActorAppState.Error(error))
        }) {
            val actorData = movieActorRepository.getMovieActorDetails(
                personId = personId,
                language = language
            )
            _movieActorData.postValue(MovieActorAppState.Success(actorData))
        }
    }

}
