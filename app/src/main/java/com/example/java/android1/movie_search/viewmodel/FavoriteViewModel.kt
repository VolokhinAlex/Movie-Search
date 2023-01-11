package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.App
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.repository.MovieLocalRepository
import com.example.java.android1.movie_search.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {

    private val _moviesFavoriteData: MutableLiveData<RoomAppState> = MutableLiveData()
    val moviesFavoriteData: LiveData<RoomAppState> = _moviesFavoriteData

    /**
     * The method for getting a list of favorite movies
     */

    fun getMoviesFromLocalDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<MovieDataRoom> =
                movieLocalRepository.getAllFavoritesFromLocalDataBase()
            _moviesFavoriteData.postValue(RoomAppState.Success(result))
        }
    }

}

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(movieLocalRepository = MovieLocalRepositoryImpl(App.movieDao)) as T
        } else {
            throw IllegalArgumentException("FavoriteViewModel not found")
        }
    }
}