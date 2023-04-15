package com.example.java.android1.movie_search.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor", foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["id"]
        )]
)
data class ActorEntity(
    @PrimaryKey
    val actorId: Long,
    val id: Long?,
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