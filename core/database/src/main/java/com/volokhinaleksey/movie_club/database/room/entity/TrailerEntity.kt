package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.TrailerDTO
import com.volokhinaleksey.movie_club.model.ui.TrailerUI

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
    val type: String?,
)

fun TrailerEntity.toTrailerDTO(): TrailerDTO {
    return TrailerDTO(name = name, key = key, type = type, id = id)
}

fun TrailerEntity.toTrailerUI(): TrailerUI {
    return TrailerUI(name = name, key = key.orEmpty(), type = type.orEmpty(), id = id)
}