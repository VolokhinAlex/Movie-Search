package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /**
     * The method for getting a list of films of a specific category using pagination
     * @param category - The category of films to get
     */

    fun getCategoryMoviesFromRemoteServer(category: String): Flow<PagingData<MovieDataTMDB>> =
        categoryRepository.getCategoryMovies(category)

}