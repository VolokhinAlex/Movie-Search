package com.volokhinaleksey.movie_club.database.room.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.GenreEntity

@Dao
interface GenresDao {

    @Upsert
    suspend fun upsertAllGenres(entities: List<GenreEntity>)

}