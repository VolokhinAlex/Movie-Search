package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.java.android1.movie_search.app.MovieActorAppState
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.repository.MovieActorRepository
import com.example.java.android1.movie_search.repository.MovieActorRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Server Error"

class MovieActorViewModel(
    private val movieActorRepository: MovieActorRepository
) : ViewModel() {

    private val _movieActorData: MutableLiveData<MovieActorAppState> = MutableLiveData()
    val movieActorData: LiveData<MovieActorAppState> = _movieActorData

    private val callback = object : Callback<ActorDTO> {
        override fun onResponse(call: Call<ActorDTO>, response: Response<ActorDTO>) {
            val serverResponse = response.body()
            _movieActorData.value = if (response.isSuccessful && serverResponse != null) {
                MovieActorAppState.Success(serverResponse)
            } else {
                MovieActorAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<ActorDTO>, error: Throwable) {
            _movieActorData.value = MovieActorAppState.Error(error)
        }
    }

    /**
     * The method for getting detailed information about an actor
     * @param personId - ID to search for an actor
     * @param language - Language to display translated data for the fields that support it.
     */

    fun getMovieActorData(personId: Long, language: String) {
        _movieActorData.value = MovieActorAppState.Loading
        movieActorRepository.getMovieActorDetailsFromRemoteServer(
            personId = personId,
            language = language,
            callback = callback
        )
    }

}

@Suppress("UNCHECKED_CAST")
class MovieActorViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieActorViewModel::class.java)) {
            MovieActorViewModel(movieActorRepository = MovieActorRepositoryImpl(RemoteDataSource())) as T
        } else {
            throw IllegalArgumentException("MovieActorViewModel not found")
        }
    }
}