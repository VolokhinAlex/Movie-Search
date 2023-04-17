package com.example.java.android1.movie_search.model.old.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trailer", foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["id"]
        )]
)
data class TrailerEntity(
    val id: String,
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val key: String?,
    val type: String?
)