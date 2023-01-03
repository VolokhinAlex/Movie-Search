package com.example.java.android1.movie_search.repository

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryResult(query: String): Flow<PagingData<MovieDataTMDB>>
}