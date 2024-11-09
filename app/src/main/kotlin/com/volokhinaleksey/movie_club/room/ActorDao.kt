package com.volokhinaleksey.movie_club.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.volokhinaleksey.movie_club.model.local.ActorEntity

@Dao
interface ActorDao {

    @Query("SELECT * FROM actor WHERE movie_id LIKE :movieId")
    suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity>

    @Query("SELECT * FROM actor WHERE actorId LIKE :actorId")
    suspend fun getActorById(actorId: Int): ActorEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ActorEntity)

    @Update
    suspend fun update(entity: ActorEntity)

    @Delete
    suspend fun delete(entity: ActorEntity)

}