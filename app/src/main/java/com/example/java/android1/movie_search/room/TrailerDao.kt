package com.example.java.android1.movie_search.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.java.android1.movie_search.model.local.TrailerEntity

@Dao
interface TrailerDao {

    @Query("SELECT * FROM trailer WHERE id LIKE :movieId")
    suspend fun getTrailerById(movieId: Int): List<TrailerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<TrailerEntity>)

    @Delete
    suspend fun delete(entity: TrailerEntity)

    @Update
    suspend fun update(entity: TrailerEntity)

}