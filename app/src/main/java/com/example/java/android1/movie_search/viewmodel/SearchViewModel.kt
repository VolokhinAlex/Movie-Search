package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    /**
     * The method for getting a list of movies by search
     * @param query - Request to find a list of movies
     */

    fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieUI>> =
        searchRepository.getMoviesBySearch(query, false)

}

