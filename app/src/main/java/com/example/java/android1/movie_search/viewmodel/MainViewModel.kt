package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    val homeLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: HomeRepository = HomeRepositoryImpl(RemoteDataSource()),
) : ViewModel() {

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            homeLiveData.value = if (response.isSuccessful && serverResponse != null) {
                AppState.Success(serverResponse.results)
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            AppState.Error(error)
        }

    }

    private fun getPopularMoviesFromRemoteSource(language: String, page: Int) {
        homeLiveData.value = AppState.Loading
        repository.getPopularMoviesFromServer(language, page, callback)
    }

    private fun getNowPlayingMoviesFromRemoteSource(language: String, page: Int) {
        homeLiveData.value = AppState.Loading
        repository.getNowPlayingMoviesFromServer(language, page, callback)
    }

    private fun getTopRatedMoviesFromRemoteSource(language: String, page: Int) {
        homeLiveData.value = AppState.Loading
        repository.getTopRatedMoviesFromServer(language, page, callback)
    }

    private fun getUpcomingMoviesFromRemoteSource(language: String, page: Int) {
        homeLiveData.value = AppState.Loading
        repository.getUpcomingMoviesFromServer(language, page, callback)
    }

    fun getCategoriesMovies(language: String, page: Int) {
        getUpcomingMoviesFromRemoteSource(language, page)
        getPopularMoviesFromRemoteSource(language, page)
        getNowPlayingMoviesFromRemoteSource(language, page)
        getTopRatedMoviesFromRemoteSource(language, page)
    }

}