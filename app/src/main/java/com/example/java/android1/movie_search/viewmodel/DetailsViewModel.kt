package com.example.java.android1.movie_search.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.App.Companion.movieDao
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.*
import com.example.java.android1.movie_search.room.MovieEntity
import com.example.java.android1.movie_search.utils.converterMovieEntityToMovieDataRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val movieLocalRepository: MovieLocalRepository = MovieLocalRepositoryImpl(movieDao),
    val movieLocalLiveData: MutableLiveData<RoomAppState> = MutableLiveData(),
) :
    ViewModel() {

    private val callback = object : Callback<MovieDataTMDB> {
        override fun onResponse(call: Call<MovieDataTMDB>, response: Response<MovieDataTMDB>) {
            val serverResponse: MovieDataTMDB? = response.body()
            detailsLiveData.value = if (response.isSuccessful && serverResponse != null) {
                checkResponse(serverResponse)
            } else {
                AppState.Error(Throwable(REQUEST_ERROR))
            }
        }

        override fun onFailure(call: Call<MovieDataTMDB>, error: Throwable) {
            detailsLiveData.value = AppState.Error(error)
        }

    }

    private fun checkResponse(movieData: MovieDataTMDB): AppState {
        return if (movieData.id != null && movieData.genres != null && movieData.release_date !=
            null && movieData.runtime != null && movieData.overview != null
        ) {
            saveMovieData(movieData)
            AppState.Success(listOf(movieData))
        } else {
            AppState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    fun getMovieDetailsFromRemoteSource(movieId: Int, language: String) {
        detailsLiveData.value = AppState.Loading
        repository.getMovieDetailsFromServer(movieId, language, callback)
    }

    private fun saveMovieData(movieData: MovieDataTMDB) = viewModelScope.launch {
        movieLocalLiveData.value = RoomAppState.Loading
        movieLocalRepository.saveMovieToLocalDataBase(movieData)
    }

    fun getMovieFromLocalDataBase(movieData: MovieDataTMDB) {
        if (movieData.id != null) {
            val handler = Handler(Looper.getMainLooper())
            viewModelScope.launch(Dispatchers.IO) {
                val result = movieLocalRepository.getMovieFromLocalDataBase(movieData.id)
                handler.post {
                    movieLocalLiveData.value = RoomAppState.Success(result)
                }
            }
        }
    }

    fun updateMovieNote(movieId: Int, note: String) = viewModelScope.launch {
        movieLocalRepository.updateMovieNoteInLocalDataBase(movieId, note)
    }

}