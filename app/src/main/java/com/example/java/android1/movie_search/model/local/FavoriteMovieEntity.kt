package com.example.java.android1.movie_search.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorite_movie", foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["id"]
    )]
)
data class FavoriteMovieEntity(
    val id: Long?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)