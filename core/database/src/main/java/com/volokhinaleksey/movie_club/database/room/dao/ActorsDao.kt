package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity

@Dao
interface ActorsDao {
    @Upsert
    suspend fun upsertActors(actorEntities: List<ActorEntity>)
    @Insert
    suspend fun insert(movieActorEntity: ActorEntity)
}