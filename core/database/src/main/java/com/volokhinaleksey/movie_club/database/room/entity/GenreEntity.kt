package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.GenreDTO
import com.volokhinaleksey.movie_club.model.ui.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

fun GenreDTO.asEntity() = GenreEntity(id = id ?: 0, name = name.orEmpty())

fun GenreEntity.asExternalModel() = Genre(id = id, name = name)