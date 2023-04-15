package com.example.java.android1.movie_search.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.java.android1.movie_search.model.local.ActorEntity

@Dao
interface ActorDao {

    @Query("SELECT * FROM actor WHERE id LIKE :movieId")
    suspend fun getActorsByMovieId(movieId: Int): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<ActorEntity>)

    @Update
    suspend fun update(entity: ActorEntity)

    @Delete
    suspend fun delete(entity: ActorEntity)

}