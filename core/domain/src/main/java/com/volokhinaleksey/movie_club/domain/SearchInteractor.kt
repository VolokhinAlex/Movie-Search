package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.repository.SearchDatabaseRepository
import com.volokhinaleksey.movie_club.data.repository.SearchRepository
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<MovieUI>>
}

class SearchInteractorImpl(
    private val searchRepository: SearchRepository,
    private val searchDatabaseRepository: SearchDatabaseRepository
) : SearchInteractor {

    override fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean,
    ): Flow<PagingData<MovieUI>> {
        //TODO(Add DB)
        return searchRepository.getMoviesByQuery(query)
    }

}