package com.example.java.android1.movie_search.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.repository.MovieLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {

    private val _localMovieLiveData: MutableLiveData<RoomAppState> = MutableLiveData()
    val localMovieLiveData: LiveData<RoomAppState> = _localMovieLiveData

    fun getMoviesFromLocalDataBase() {
        val handler = Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<MovieDataRoom> = movieLocalRepository.getAllFavorites()
            handler.post {
                _localMovieLiveData.value = RoomAppState.Success(result)
            }
        }
    }
}

class FavoriteViewModelFactory(private val repository: MovieLocalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not Found")
        }
    }
}