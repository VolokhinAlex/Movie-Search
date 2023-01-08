package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /**
     * The method for getting a list of films of a specific category using pagination
     */

    fun getCategoryMoviesFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        categoryRepository.getCategoryMoviesFromRemoteServer(query)

}

@Suppress("UNCHECKED_CAST")
class CategoryMoviesViewModelFactory(private val repository: CategoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryMoviesViewModel::class.java)) {
            CategoryMoviesViewModel(repository) as T
        } else {
            throw IllegalArgumentException("CategoryMoviesViewModel not found")
        }
    }
}