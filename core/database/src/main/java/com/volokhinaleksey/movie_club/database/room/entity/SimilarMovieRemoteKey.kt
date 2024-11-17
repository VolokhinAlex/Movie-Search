package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "similar_movie_remote_key"
)
data class SimilarMovieRemoteKey(
    @PrimaryKey
    @ColumnInfo("movie_id") val movieId: Int,
    @ColumnInfo("next_page") val nextPage: Int?
)
