package com.example.java.android1.movie_search.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "similar_movie", foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"]
        )]
)
data class SimilarMovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "similar_movie_id")
    val similarMovieId: Long,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    val name: String
)
