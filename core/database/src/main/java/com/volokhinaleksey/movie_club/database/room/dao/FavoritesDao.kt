package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Upsert
    suspend fun saveFavoriteMovie(entity: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE favorite = 1")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query(
        """
            SELECT 
            CASE WHEN EXISTS(SELECT * FROM favorites WHERE movie_id = :movieId AND favorite = 1) 
                THEN 1 ELSE 0 END
        """
    )
    suspend fun isMovieFavorite(movieId: Int): Boolean
}