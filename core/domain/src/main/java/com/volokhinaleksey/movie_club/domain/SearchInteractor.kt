package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.repository.SearchRepository
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<Movie>>
}

class SearchInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchInteractor {

    override fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean,
    ): Flow<PagingData<Movie>> {
        //TODO(Add DB)
        return searchRepository.getMoviesByQuery(query)
    }

}