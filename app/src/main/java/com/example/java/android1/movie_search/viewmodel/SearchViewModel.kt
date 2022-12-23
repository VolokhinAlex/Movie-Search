package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.movie_search.app.App.Companion.historySearchDao
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class SearchViewModel(
    val searchLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: SearchRepository = SearchRepositoryImpl(RemoteDataSource()),
    private val localSearchRepository: LocalSearchRepository = LocalSearchRepositoryImpl(historySearchDao)
) : ViewModel() {

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            searchLiveData.value = if (response.isSuccessful && serverResponse != null) {
                AppState.Success(serverResponse.results)
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            searchLiveData.value = AppState.Error(error)
        }

    }

    fun getMoviesFromRemoteSource(language: String, page: Int, adult: Boolean, query: String) {
        searchLiveData.value = AppState.Loading
        repository.getMoviesFromServer(
            language = language,
            page = page,
            adult = adult,
            query = query,
            callback = callback
        )
    }

    fun saveSearchRequest(query: String, date: Long) = viewModelScope.launch {
        localSearchRepository.saveEntity(query, date)
    }

}