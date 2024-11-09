package com.volokhinaleksey.movie_club.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.repository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /**
     * The method for getting a list of films of a specific category using pagination
     * @param category - The category of films to get
     */

    fun getCategoryMoviesFromRemoteServer(
        category: String,
        isOnline: Boolean
    ): Flow<PagingData<MovieUI>> =
        categoryRepository.getCategoryMovies(
            categoryMovies = category,
            isNetworkAvailable = isOnline
        ).cachedIn(viewModelScope)

}