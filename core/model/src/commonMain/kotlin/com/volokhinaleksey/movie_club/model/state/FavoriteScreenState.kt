package com.volokhinaleksey.movie_club.model.state

import com.volokhinaleksey.movie_club.model.ui.Movie

sealed interface FavoriteScreenState {
    data object Loading : FavoriteScreenState
    data class Success(val data: List<Movie>) : FavoriteScreenState
}