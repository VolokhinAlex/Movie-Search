package com.volokhinaleksey.movie_club.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.domain.HomeInteractor
import com.volokhinaleksey.movie_club.domain.LocaleInteractor
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    homeInteractor: HomeInteractor,
    localeInteractor: LocaleInteractor
) : ViewModel() {

    val popularMovies = homeInteractor.getMovies(categoryId = MovieCategory.Popular.id)
        .map(MovieCategoryState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2_000),
            initialValue = MovieCategoryState.Loading
        )

    val nowPlayingMovies = homeInteractor.getMovies(categoryId = MovieCategory.NowPlaying.id)
        .map(MovieCategoryState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2_000),
            initialValue = MovieCategoryState.Loading
        )

    val topRatedMovies = homeInteractor.getMovies(categoryId = MovieCategory.TopRated.id)
        .map(MovieCategoryState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2_000),
            initialValue = MovieCategoryState.Loading
        )

    val upcomingMovies = homeInteractor.getMovies(categoryId = MovieCategory.Upcoming.id)
        .map(MovieCategoryState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2_000),
            initialValue = MovieCategoryState.Loading
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            MovieCategory.entries.forEach {
                launch {
                    homeInteractor.syncData(it.id, localeInteractor.getCurrentLanguage())
                }
            }
        }
    }
}