package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.view.compose.home.Category
import com.example.java.android1.movie_search.view.compose.home.CategoryAppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _homeLiveData: MutableLiveData<CategoryAppState> = MutableLiveData()
    val homeLiveData: LiveData<CategoryAppState> = _homeLiveData

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            _homeLiveData.value = if (response.isSuccessful && serverResponse != null) {
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
            _homeLiveData.value = CategoryAppState.Error(error)
        }

    }

    fun getMovieCategory(category: String, language: String, page: Int) {
        _homeLiveData.value = CategoryAppState.Loading
        repository.getMoviesCategoryFromRemoteServer(category, language, page, callback)
    }

}

class MainViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}