package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.app.MovieActorState
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.repository.MovieActorRepository
import com.example.java.android1.movie_search.repository.MovieActorRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MovieActorViewModel(
    val movieActorLiveData: MutableLiveData<MovieActorState> = MutableLiveData(),
    private val movieActorRepository: MovieActorRepository = MovieActorRepositoryImpl(
        RemoteDataSource()
    ),
) : ViewModel() {

    private val callback = object : Callback<ActorDTO> {
        override fun onResponse(call: Call<ActorDTO>, response: Response<ActorDTO>) {
            val serverResponse = response.body()
            movieActorLiveData.value = if (response.isSuccessful && serverResponse != null) {
                MovieActorState.Success(serverResponse)
            } else {
                MovieActorState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<ActorDTO>, error: Throwable) {
            movieActorLiveData.value = MovieActorState.Error(error)
        }
    }

    fun getMovieActorData(personId: Long, language: String) {
        movieActorLiveData.value = MovieActorState.Loading
        movieActorRepository.getMovieActor(
            personId = personId,
            language = language,
            callback = callback
        )
    }
}