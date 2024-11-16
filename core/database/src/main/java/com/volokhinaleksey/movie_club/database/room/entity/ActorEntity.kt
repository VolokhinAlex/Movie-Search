package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.ui.Actor

@Entity(
    tableName = "movie_actors",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("movie_id"), Index("id")]
)
data class ActorEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("movie_id") val movieId: Int,
    val biography: String = "",
    val birthday: String = "",
    @ColumnInfo("imdb_id") val imdbId: String = "",
    val name: String = "",
    @ColumnInfo("place_of_birth") val placeOfBirth: String = "",
    val popularity: Double = 0.0,
    @ColumnInfo("profile_path") val profilePath: String = "",
    val character: String
)

fun ActorDTO.asEntity(movieId: Int) = ActorEntity(
    id = id ?: 0L,
    movieId = movieId,
    biography = biography.orEmpty(),
    birthday = birthday.orEmpty(),
    imdbId = imdbId.orEmpty(),
    name = name.orEmpty(),
    placeOfBirth = placeOfBirth.orEmpty(),
    popularity = popularity ?: 0.0,
    profilePath = profilePath.orEmpty(),
    character = character.orEmpty()
)

fun ActorEntity.asExternalModel() = Actor(
    actorId = id,
    movieId = movieId.toLong(),
    biography = biography,
    birthday = birthday,
    imdbId = imdbId,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    character = character
)