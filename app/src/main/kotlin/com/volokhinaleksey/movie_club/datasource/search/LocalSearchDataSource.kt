package com.volokhinaleksey.movie_club.datasource.search

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData

interface LocalSearchDataSource : SearchDataSource<PagingData<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}