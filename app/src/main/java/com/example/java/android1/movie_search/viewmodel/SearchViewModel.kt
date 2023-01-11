package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.repository.SearchRepository
import com.example.java.android1.movie_search.repository.SearchRepositoryImpl
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    /**
     * The method for getting a list of movies by search
     * @param query - Request to find a list of movies
     */

    fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        searchRepository.getMoviesBySearchFromRemoteServer(query)

}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(searchRepository = SearchRepositoryImpl(RemoteDataSource())) as T
        } else {
            throw IllegalArgumentException("SearchViewModel not found")
        }
    }
}