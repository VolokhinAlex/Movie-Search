package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.states.RoomAppState
import com.example.java.android1.movie_search.repository.favorite.FavoriteRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _moviesFavoriteData: MutableLiveData<RoomAppState> = MutableLiveData()
    val moviesFavoriteData: LiveData<RoomAppState> = _moviesFavoriteData

    /**
     * The method for getting a list of favorite movies
     */

    fun getMoviesFromLocalDataBase() {
        _moviesFavoriteData.value = RoomAppState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _moviesFavoriteData.postValue(RoomAppState.Error(error))
        }) {
            val result: List<MovieDataRoom> = favoriteRepository.getAllFavorites()
            _moviesFavoriteData.postValue(RoomAppState.Success(result))
        }
    }

}
