package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    fun getCategoryMoviesFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        repository.getCategoryMoviesFromRemoteServer(query)

}

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