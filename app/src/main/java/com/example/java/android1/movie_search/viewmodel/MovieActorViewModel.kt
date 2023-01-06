package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.java.android1.movie_search.app.MovieActorState
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.repository.MovieActorRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MovieActorViewModel(
    private val movieActorRepository: MovieActorRepository
) : ViewModel() {

    private val _movieActorLiveData: MutableLiveData<MovieActorState> = MutableLiveData()
    val movieActorLiveData: LiveData<MovieActorState> = _movieActorLiveData

    private val callback = object : Callback<ActorDTO> {
        override fun onResponse(call: Call<ActorDTO>, response: Response<ActorDTO>) {
            val serverResponse = response.body()
            _movieActorLiveData.value = if (response.isSuccessful && serverResponse != null) {
                MovieActorState.Success(serverResponse)
            } else {
                MovieActorState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<ActorDTO>, error: Throwable) {
            _movieActorLiveData.value = MovieActorState.Error(error)
        }
    }

    fun getMovieActorData(personId: Long, language: String) {
        _movieActorLiveData.value = MovieActorState.Loading
        movieActorRepository.getMovieActorFromRemoteServer(
            personId = personId,
            language = language,
            callback = callback
        )
    }
}

class MovieActorViewModelFactory(private val repository: MovieActorRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieActorViewModel::class.java)) {
            MovieActorViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not Found")
        }
    }
}