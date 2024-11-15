package com.volokhinaleksey.movie_club.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.repository.favorite.FavoriteRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _moviesFavoriteData: MutableLiveData<MovieState> = MutableLiveData()
    val moviesFavoriteData: LiveData<MovieState> = _moviesFavoriteData

    /**
     * The method for getting a list of favorite movies
     */

    fun getMoviesFromLocalDataBase() {
        _moviesFavoriteData.value = MovieState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _moviesFavoriteData.postValue(MovieState.Error(error))
        }) {
            _moviesFavoriteData.postValue(favoriteRepository.getAllFavorites())
        }
    }

}
