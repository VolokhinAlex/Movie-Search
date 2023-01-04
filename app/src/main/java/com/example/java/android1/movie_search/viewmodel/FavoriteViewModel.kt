package com.example.java.android1.movie_search.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.movie_search.app.App.Companion.movieDao
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.repository.MovieLocalRepository
import com.example.java.android1.movie_search.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieLocalRepository: MovieLocalRepository = MovieLocalRepositoryImpl(movieDao),
    val localMovieLiveData: MutableLiveData<RoomAppState> = MutableLiveData()
) : ViewModel() {

    init {
        getMoviesFromLocalDataBase()
    }

    private fun getMoviesFromLocalDataBase() {
        val handler = Handler(Looper.getMainLooper())
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<MovieDataRoom> = movieLocalRepository.getAllFavorites()
            handler.post {
                localMovieLiveData.value = RoomAppState.Success(result)
            }
        }
    }
}