package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.ui.Favorite

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo("movie_id") val movieId: Int,
    val favorite: Boolean
)

fun Favorite.asEntity() = FavoriteEntity(movieId = movieId, favorite = isFavorite)
