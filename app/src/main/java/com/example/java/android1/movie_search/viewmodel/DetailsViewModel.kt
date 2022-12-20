package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.DetailsRepository
import com.example.java.android1.movie_search.repository.DetailsRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
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
    )
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
        return if (movieData.genres != null && movieData.release_date != null && movieData.runtime != null && movieData.overview != null) {
            AppState.Success(listOf(movieData))
        } else {
            AppState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    fun getMovieDetailsFromRemoteSource(movieId: Int, language: String) {
        detailsLiveData.value = AppState.Loading
        repository.getMovieDetailsFromServer(movieId, language, callback)
    }


}