package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.CategoryRepository
import com.example.java.android1.movie_search.repository.CategoryRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /**
     * The method for getting a list of films of a specific category using pagination
     * @param category - The category of films to get
     */

    fun getCategoryMoviesFromRemoteServer(category: String): Flow<PagingData<MovieDataTMDB>> =
        categoryRepository.getCategoryMoviesFromRemoteServer(category)

}

@Suppress("UNCHECKED_CAST")
class CategoryMoviesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryMoviesViewModel::class.java)) {
            CategoryMoviesViewModel(categoryRepository = CategoryRepositoryImpl(RemoteDataSource())) as T
        } else {
            throw IllegalArgumentException("CategoryMoviesViewModel not found")
        }
    }
}