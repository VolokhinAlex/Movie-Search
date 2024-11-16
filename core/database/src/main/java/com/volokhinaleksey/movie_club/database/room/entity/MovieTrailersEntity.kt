package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.TrailerDTO
import com.volokhinaleksey.movie_club.model.ui.Trailer

@Entity(
    tableName = "movie_trailers",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("movie_id")]
)
data class MovieTrailersEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("movie_id") val movieId: Int,
    val name: String = "",
    val key: String = "",
    val type: String = "",
)

fun TrailerDTO.asEntity(movieId: Int) = MovieTrailersEntity(
    id = id.orEmpty(),
    movieId = movieId,
    name = name.orEmpty(),
    key = key.orEmpty(),
    type = type.orEmpty()
)

fun MovieTrailersEntity.asExternalModel() = Trailer(
    id = id,
    name = name,
    key = key,
    type = type
)