package com.example.java.android1.movie_search.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_search")
data class HistorySearchEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "movie_title")
    val movieTitle: String,
    @ColumnInfo(name = "search_date")
    val dateTime: Long

)
