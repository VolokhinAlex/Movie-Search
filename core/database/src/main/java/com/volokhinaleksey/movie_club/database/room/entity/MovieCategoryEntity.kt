package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movie_category",
    primaryKeys = ["category_id", "movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["category_id"]), Index(value = ["movie_id"])]
)
data class MovieCategoryEntity(
    @ColumnInfo("category_id")  val categoryId: String,
    @ColumnInfo("movie_id") val movieId: Int
)
