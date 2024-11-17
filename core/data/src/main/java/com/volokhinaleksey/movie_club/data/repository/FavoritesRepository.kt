package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.FavoriteEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<Favorite>>
}

class FavoritesRepositoryImpl(
    private val dataBase: MovieDataBase,
) : FavoritesRepository {

    override fun getAllFavorites(): Flow<List<Favorite>> =
        dataBase.favoritesDao().getFavorites().map { it.map(FavoriteEntity::asExternalModel) }

}