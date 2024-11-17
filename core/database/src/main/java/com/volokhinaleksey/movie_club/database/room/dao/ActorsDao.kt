package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertActors(actorEntities: List<ActorEntity>)

    @Upsert
    suspend fun upsert(movieActorEntity: ActorEntity)

    @Query("UPDATE movie_actors SET biography = :biography, birthday = :birthday WHERE id = :actorId")
    suspend fun update(
        actorId: Long,
        biography: String,
        birthday: String,
    )

    @Query("SELECT * FROM movie_actors WHERE id = :actorId")
    suspend fun getActorById(actorId: Long): ActorEntity?
}