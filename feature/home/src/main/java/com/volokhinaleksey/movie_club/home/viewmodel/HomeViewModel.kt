package com.volokhinaleksey.movie_club.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.domain.HomeInteractor
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeInteractor: HomeInteractor
) : ViewModel() {
    
    private val _popularMovies = MutableSharedFlow<List<MovieUI>>()
    val popularMovies: SharedFlow<List<MovieUI>> get() = _popularMovies

    private val _nowPlayingMovies = MutableSharedFlow<List<MovieUI>>()
    val nowPlayingMovies: SharedFlow<List<MovieUI>> get() = _nowPlayingMovies

    private val _topRatedMovies = MutableSharedFlow<List<MovieUI>>()
    val topRatedMovies: SharedFlow<List<MovieUI>> get() = _topRatedMovies

    private val _upcomingMovies = MutableSharedFlow<List<MovieUI>>()
    val upcomingMovies: SharedFlow<List<MovieUI>> get() = _upcomingMovies

    fun loadAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val language = "en-EN"
            val isOnline = true
            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.Popular.id,
                    language = language,
                    page = 1,
                    isLocalSource = isOnline
                )
                if (result is MovieCategoryState.Success) { _popularMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.NowPlaying.id,
                    language = language,
                    page = 1,
                    isLocalSource = isOnline
                )
                if (result is MovieCategoryState.Success) { _nowPlayingMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.Upcoming.id,
                    language = language,
                    page = 1,
                    isLocalSource = isOnline
                )
                if (result is MovieCategoryState.Success) { _topRatedMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.TopRated.id,
                    language = language,
                    page = 1,
                    isLocalSource = isOnline
                )
                if (result is MovieCategoryState.Success) { _upcomingMovies.emit(result.data) }
            }
        }
    }
    
}