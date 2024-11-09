package com.volokhinaleksey.movie_club.datasource.category

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData

interface LocalCategoryDataSource : CategoryDataSource<PagingData<LocalMovieData>> {

    suspend fun saveMovie(movie: LocalMovieData)

}