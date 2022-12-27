package com.example.java.android1.movie_search.view.compose.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.viewmodel.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModelCompose(
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

    fun getMovieCategory(category: String, language: String, page: Int) {
        homeLiveData.value = AppState.Loading
        repository.getMoviesCategoryForCompose(category, language, page, callback)
    }

}