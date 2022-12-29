package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.view.compose.home.Category
import com.example.java.android1.movie_search.view.compose.home.CategoryAppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"


class MainViewModel(
    val homeLiveData: MutableLiveData<CategoryAppState> = MutableLiveData(),
    private val repository: HomeRepository = HomeRepositoryImpl(RemoteDataSource()),
) : ViewModel() {

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            homeLiveData.value = if (response.isSuccessful && serverResponse != null) {
                val request = call.request().url.toString()
                val queryName = request.substring(request.indexOf("movie/"), request.indexOf("?"))
                CategoryAppState.Success(
                    Category(
                        queryName.substring(queryName.indexOf("/") + 1),
                        serverResponse.results
                    )
                )
            } else {
                CategoryAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            homeLiveData.value = CategoryAppState.Error(error)
        }

    }

    fun getCategoryMovies(category: String, language: String, page: Int) {
        homeLiveData.value = CategoryAppState.Loading
        repository.getMoviesCategoryForCompose(category, language, page, callback)
    }

}