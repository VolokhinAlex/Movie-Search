package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.java.android1.movie_search.model.state.MovieState
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.repository.details.DetailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val _detailsMovieData: MutableLiveData<MovieState> = MutableLiveData()
    val detailsMovieData: LiveData<MovieState> = _detailsMovieData

    /**
     * Method for getting details about the movie
     * @param movieId - id of the movie to get the details of
     * @param language - Response language
     */

    fun getMovieDetails(movieId: Int, language: String, isOnline: Boolean) {
        _detailsMovieData.value = MovieState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _detailsMovieData.postValue(MovieState.Error(error))
        }) {
            val response = detailsRepository.getMovieDetails(
                movieId = movieId, language = language, isNetworkAvailable = isOnline, category = ""
            )
            _detailsMovieData.postValue(response)
        }
    }

    /**
     * Method for adding a movie to the list of favorite movies
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    fun setFavoriteMovie(movieId: Int, favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.updateMovie(movieId, favorite)
        }
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    fun getSimilarMovies(movieId: Int, isOnline: Boolean): Flow<PagingData<MovieUI>> =
        detailsRepository.getSimilarMovies(movieId = movieId, isNetworkAvailable = isOnline)
            .cachedIn(viewModelScope)


}