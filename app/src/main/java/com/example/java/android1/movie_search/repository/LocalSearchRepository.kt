package com.example.java.android1.movie_search.repository

import kotlinx.coroutines.flow.Flow

/**
 * Interface for working with a local database. There are two methods in this interface.
 * 1. To record the search history
 * 2. To get the user's search history
 */

interface LocalSearchRepository {

    fun getHistorySearch() : Flow<List<String>>
    suspend fun saveEntity(movieTitle: String, date: Long)

}