package com.example.java.android1.movie_search.datasource.category

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalCategoryDataSource : CategoryDataSource<PagingData<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}