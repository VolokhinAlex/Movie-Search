package com.volokhinaleksey.movie_club.datasource.search

import kotlinx.coroutines.flow.Flow

interface SearchDataSource<T> {

    fun getMoviesByQuery(query: String): Flow<T>

}