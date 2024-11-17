package com.volokhinaleksey.movie_club.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.domain.FavoritesInteractor
import com.volokhinaleksey.movie_club.model.state.FavoriteScreenState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    val favoriteScreenState: StateFlow<FavoriteScreenState> = favoritesInteractor.getFavorites()
        .map(FavoriteScreenState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = FavoriteScreenState.Loading
        )

}