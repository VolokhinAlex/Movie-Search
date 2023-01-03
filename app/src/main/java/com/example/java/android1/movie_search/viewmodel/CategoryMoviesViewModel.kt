package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.CategoryRepository
import com.example.java.android1.movie_search.repository.CategoryRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class CategoryMoviesViewModel(
    private val repository: CategoryRepository = CategoryRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getCategoryMoviesFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        repository.getCategoryResult(query)
}