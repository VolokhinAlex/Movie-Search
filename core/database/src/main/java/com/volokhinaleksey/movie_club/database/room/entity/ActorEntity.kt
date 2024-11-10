package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.CastDTO
import com.volokhinaleksey.movie_club.model.ui.ActorUI

@Entity(
    tableName = "actor", foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"]
        )]
)
data class ActorEntity(
    @PrimaryKey
    val actorId: Long,
    @ColumnInfo(name = "movie_id")
    val movieId: Long?,
    val biography: String?,
    val birthday: String?,
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
    val name: String?,
    @ColumnInfo(name = "place_of_birth")
    val placeOfBirth: String?,
    val popularity: Double?,
    @ColumnInfo(name = "profile_path")
    val profilePath: String?,
    val character: String?
)

fun ActorEntity.toCastDTO(): CastDTO {
    return CastDTO(
        id = movieId,
        name = name,
        profile_path = profilePath,
        character = character
    )
}

fun ActorEntity.toActorUI(): ActorUI {
    return ActorUI(
        movieId = movieId ?: 0,
        name = name.orEmpty(),
        profilePath = profilePath.orEmpty(),
        character = character.orEmpty()
    )
}