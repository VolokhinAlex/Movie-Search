package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "similar_movie",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("similar_id"), Index("movie_id")],
    primaryKeys = ["similar_id", "movie_id"]
)
data class SimilarMovieEntity(
    @ColumnInfo("similar_id") val similarId: Int,
    @ColumnInfo("movie_id") val movieId: Int,
)