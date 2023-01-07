package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.LocalSearchRepository
import com.example.java.android1.movie_search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository,
    private val localSearchRepository: LocalSearchRepository
) : ViewModel() {

    private val _searchLiveData: MutableLiveData<MovieAppState> = MutableLiveData()
    val searchLiveData: LiveData<MovieAppState> = _searchLiveData

    fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        repository.getMoviesBySearchFromRemoteServer(query)

    fun saveSearchRequest(query: String, date: Long) = viewModelScope.launch {
        localSearchRepository.saveEntity(query, date)
    }

}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val repository: SearchRepository,
    private val localSearchRepository: LocalSearchRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(repository, localSearchRepository) as T
        } else {
            throw IllegalArgumentException("SearchViewModel not found")
        }
    }
}