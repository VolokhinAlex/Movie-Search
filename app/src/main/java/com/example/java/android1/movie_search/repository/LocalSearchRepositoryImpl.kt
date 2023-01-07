package com.example.java.android1.movie_search.repository

import androidx.annotation.WorkerThread
import com.example.java.android1.movie_search.room.HistorySearchDao
import com.example.java.android1.movie_search.room.HistorySearchEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the interface for getting and reading data to Local Database
 */

class LocalSearchRepositoryImpl(
    private var localDataSource: HistorySearchDao
) : LocalSearchRepository {

    override fun getHistorySearch(): Flow<List<String>> =
        localDataSource.all().map { it.map { it.movieTitle } }

    @WorkerThread
    override suspend fun saveEntity(movieTitle: String, date: Long) {
        localDataSource.insert(HistorySearchEntity(0, movieTitle, date))
    }

}