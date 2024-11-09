package com.volokhinaleksey.movie_club.repository.favorite

import com.volokhinaleksey.movie_club.model.state.MovieState

interface FavoriteRepository {

    suspend fun getAllFavorites(): MovieState

}