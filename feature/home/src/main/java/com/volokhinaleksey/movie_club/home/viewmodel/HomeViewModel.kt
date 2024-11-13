package com.volokhinaleksey.movie_club.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.domain.HomeInteractor
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeInteractor: HomeInteractor
) : ViewModel() {
    
    private val _popularMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val popularMovies get() = _popularMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val nowPlayingMovies get() = _nowPlayingMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val topRatedMovies get() = _topRatedMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val upcomingMovies get() = _upcomingMovies.asStateFlow()

    fun loadAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val language = "en-EN"
            val isLocalSource = false
            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.Popular.id,
                    language = language,
                    page = 1,
                    isLocalSource = isLocalSource
                )
                if (result is MovieCategoryState.Success) { _popularMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.NowPlaying.id,
                    language = language,
                    page = 1,
                    isLocalSource = isLocalSource
                )
                if (result is MovieCategoryState.Success) { _nowPlayingMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.Upcoming.id,
                    language = language,
                    page = 1,
                    isLocalSource = isLocalSource
                )
                if (result is MovieCategoryState.Success) { _topRatedMovies.emit(result.data) }
            }

            launch {
                val result = homeInteractor.getMovies(
                    categoryId = MovieCategory.TopRated.id,
                    language = language,
                    page = 1,
                    isLocalSource = isLocalSource
                )
                if (result is MovieCategoryState.Success) { _upcomingMovies.emit(result.data) }
            }
        }
    }
    
}