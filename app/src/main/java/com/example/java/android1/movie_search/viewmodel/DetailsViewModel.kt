package com.example.java.android1.movie_search.viewmodel

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
) : ViewModel() {

    private val _detailsMovieData: MutableLiveData<MovieAppState> = MutableLiveData()
    val detailsMovieData: LiveData<MovieAppState> = _detailsMovieData
    private val _movieLocalData: MutableLiveData<RoomAppState> = MutableLiveData()
    val movieLocalData: LiveData<RoomAppState> = _movieLocalData

    private val callback = object : Callback<MovieDataTMDB> {
        override fun onResponse(call: Call<MovieDataTMDB>, response: Response<MovieDataTMDB>) {
            val serverResponse: MovieDataTMDB? = response.body()
            _detailsMovieData.value = if (response.isSuccessful && serverResponse != null) {
                getMovieDetailsFromLocalDataBase(serverResponse)
                checkResponse(serverResponse)
            } else {
                MovieAppState.Error(Throwable(REQUEST_ERROR))
            }
        }

        override fun onFailure(call: Call<MovieDataTMDB>, error: Throwable) {
            _detailsMovieData.value = MovieAppState.Error(error)
        }
    }

    private fun checkResponse(movieData: MovieDataTMDB): MovieAppState {
        return if (movieData.id != null && movieData.genres != null && movieData.release_date !=
            null && movieData.runtime != null && movieData.overview != null
        ) {
            saveMovieDataToLocalBase(movieData)
            MovieAppState.Success(listOf(movieData))
        } else {
            MovieAppState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    fun getMovieDetailsFromRemoteSource(movieId: Int, language: String) {
        _detailsMovieData.value = MovieAppState.Loading
        repository.getMovieDetailsFromRemoteServer(movieId, language, callback)
    }

    private fun saveMovieDataToLocalBase(movieData: MovieDataTMDB) = viewModelScope.launch {
        _movieLocalData.value = RoomAppState.Loading
        movieLocalRepository.saveMovieToLocalDataBase(movieData)
    }

    fun getMovieDetailsFromLocalDataBase(movieData: MovieDataTMDB) {
        if (movieData.id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = movieLocalRepository.getMovieFromLocalDataBase(movieData.id)
                _movieLocalData.postValue(RoomAppState.Success(listOf(result)))
            }
        }
    }

    fun setFavoriteMovie(movieId: Int, favorite: Boolean) = viewModelScope.launch {
        movieLocalRepository.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }

}

@Suppress("UNCHECKED_CAST")
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
            throw IllegalArgumentException("DetailsViewModel not found")
        }
    }
}