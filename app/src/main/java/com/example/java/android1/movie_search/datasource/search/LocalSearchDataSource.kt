package com.example.java.android1.movie_search.datasource.search

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.local.LocalMovieData

interface LocalSearchDataSource : SearchDataSource<PagingData<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}