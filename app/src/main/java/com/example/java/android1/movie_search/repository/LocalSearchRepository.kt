package com.example.java.android1.movie_search.repository

import kotlinx.coroutines.flow.Flow

interface LocalSearchRepository {

    fun getHistorySearch() : Flow<List<String>>
    suspend fun saveEntity(movieTitle: String, date: Long)

}