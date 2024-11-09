package com.volokhinaleksey.movie_club.datasource.category

import kotlinx.coroutines.flow.Flow

interface CategoryDataSource<T> {

    fun getCategoryMovies(categoryMovies: String): Flow<T>

}