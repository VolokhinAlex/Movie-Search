package com.example.java.android1.movie_search.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchDao {

    @Query("SELECT * FROM history_search ORDER BY id DESC LIMIT 10")
    fun all() : Flow<List<HistorySearchEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: HistorySearchEntity)

}