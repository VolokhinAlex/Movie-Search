package com.example.java.android1.movie_search.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.DetailsRepository
import com.example.java.android1.movie_search.repository.MovieLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    private val repository: DetailsRepository,
    private val movieLocalRepository: MovieLocalRepository
) :
    ViewModel() {

    private val _detailsLiveData: MutableLiveData<MovieAppState> = MutableLiveData()
    val detailsLiveData: LiveData<MovieAppState> = _detailsLiveData
    private val _movieLocalLiveData: MutableLiveData<RoomAppState> = MutableLiveData()
    val movieLocalLiveData: LiveData<RoomAppState> = _movieLocalLiveData

    private val callback = object : Callback<MovieDataTMDB> {
        override fun onResponse(call: Call<MovieDataTMDB>, response: Response<MovieDataTMDB>) {
            val serverResponse: MovieDataTMDB? = response.body()
            _detailsLiveData.value = if (response.isSuccessful && serverResponse != null) {
                getMovieFromLocalDataBase(serverResponse)
                checkResponse(serverResponse)
            } else {
                MovieAppState.Error(Throwable(REQUEST_ERROR))
            }
        }

        override fun onFailure(call: Call<MovieDataTMDB>, error: Throwable) {
            _detailsLiveData.value = MovieAppState.Error(error)
        }

    }

    private fun checkResponse(movieData: MovieDataTMDB): MovieAppState {
        return if (movieData.id != null && movieData.genres != null && movieData.release_date !=
            null && movieData.runtime != null && movieData.overview != null
        ) {
            saveMovieData(movieData)
            MovieAppState.Success(listOf(movieData))
        } else {
            MovieAppState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    fun getMovieDetailsFromRemoteSource(movieId: Int, language: String) {
        _detailsLiveData.value = MovieAppState.Loading
        repository.getMovieDetailsFromRemoteServer(movieId, language, callback)
    }

    private fun saveMovieData(movieData: MovieDataTMDB) = viewModelScope.launch {
        _movieLocalLiveData.value = RoomAppState.Loading
        movieLocalRepository.saveMovieToLocalDataBase(movieData)
    }

    fun getMovieFromLocalDataBase(movieData: MovieDataTMDB) {
        if (movieData.id != null) {
            val handler = Handler(Looper.getMainLooper())
            viewModelScope.launch(Dispatchers.IO) {
                val result = movieLocalRepository.getMovieFromLocalDataBase(movieData.id)
                handler.post {
                    _movieLocalLiveData.value = RoomAppState.Success(listOf(result))
                }
            }
        }
    }

    fun updateMovieNote(movieId: Int, note: String) = viewModelScope.launch {
        movieLocalRepository.updateMovieNoteInLocalDataBase(movieId, note)
    }

    fun updateMovieFavorite(movieId: Int, favorite: Boolean) = viewModelScope.launch {
        movieLocalRepository.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }

}

class DetailsViewModelFactory(
    private val repositoryRemote: DetailsRepository,
    private val localRepository: MovieLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(
                repository = repositoryRemote,
                movieLocalRepository = localRepository
            ) as T
        } else {
            throw IllegalArgumentException("Not Found")
        }
    }
}