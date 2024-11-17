package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertActors(actorEntities: List<ActorEntity>)

    @Upsert
    suspend fun upsert(movieActorEntity: ActorEntity)
}