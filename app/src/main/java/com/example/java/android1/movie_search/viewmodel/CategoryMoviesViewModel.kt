package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class CategoryMoviesViewModel(
    private val remoteRepository: HomeRepository = HomeRepositoryImpl(
        RemoteDataSource()
    ),
    val categoryMoviesLiveData: MutableLiveData<MovieAppState> = MutableLiveData()
) : ViewModel() {

    private val callback = object : Callback<CategoryMoviesTMDB> {
        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            categoryMoviesLiveData.value = if (response.isSuccessful && serverResponse != null) {
                MovieAppState.Success(serverResponse.results)
            } else {
                MovieAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            categoryMoviesLiveData.value = MovieAppState.Error(error)
        }

    }

    fun getCategoryMoviesFromRemoteServer(category: String, language: String, page: Int) {
//        categoryMoviesLiveData.value = MovieAppState.Loading
        remoteRepository.getMoviesCategoryForCompose(category, language, page, callback)
    }

}