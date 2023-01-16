package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.java.android1.movie_search.app.App.Companion.historySearchDao
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class SearchViewModel(
    val searchLiveData: MutableLiveData<MovieAppState> = MutableLiveData(),
    private val repository: SearchRepository = SearchRepositoryImpl(RemoteDataSource()),
    private val localSearchRepository: LocalSearchRepository = LocalSearchRepositoryImpl(historySearchDao),
    val liveDataHistory: LiveData<List<String>> = localSearchRepository.getHistorySearch().asLiveData()
) : ViewModel() {

    fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        repository.getSearchRequest(query)

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            searchLiveData.value = if (response.isSuccessful && serverResponse != null) {
                MovieAppState.Success(serverResponse.results)
            } else {
                MovieAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            searchLiveData.value = MovieAppState.Error(error)
        }

    }

    fun getMoviesFromRemoteSource(language: String, page: Int, adult: Boolean, query: String) {
        searchLiveData.value = MovieAppState.Loading
        repository.getMoviesFromRemoteServer(
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