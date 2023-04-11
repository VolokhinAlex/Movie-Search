package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.model.states.MovieDataAppState
import com.example.java.android1.movie_search.model.states.LocalMovieAppState
import com.example.java.android1.movie_search.repository.details.DetailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val CORRUPTED_DATA = "Incomplete data"

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val _detailsMovieData: MutableLiveData<MovieDataAppState> = MutableLiveData()
    val detailsMovieData: LiveData<MovieDataAppState> = _detailsMovieData
    private val _movieLocalData: MutableLiveData<LocalMovieAppState> = MutableLiveData()
    val movieLocalData: LiveData<LocalMovieAppState> = _movieLocalData

    /**
     * The method for checking the completeness of the received data
     */

    private fun checkResponse(movieData: MovieDataTMDB): MovieDataAppState {
        return if (movieData.id != null && movieData.genres != null && movieData.release_date !=
            null && movieData.runtime != null && movieData.overview != null
        ) {
            saveMovieDataToLocalBase(movieData)
            MovieDataAppState.Success(movieData)
        } else {
            MovieDataAppState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    /**
     * Method for getting details about the movie
     * @param movieId - id of the movie to get the details of
     * @param language - Response language
     */

    fun getMovieDetails(movieId: Int, language: String) {
        _detailsMovieData.value = MovieDataAppState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            _detailsMovieData.postValue(MovieDataAppState.Error(error))
        }) {
            val response = detailsRepository.getMovieDetails(movieId, language)
            getMovieDetailsFromLocalDataBase(response)
            _detailsMovieData.postValue(checkResponse(response))
        }
    }

    /**
     * Method for saving a movie to a local database
     * @param movieData - The movie to save
     */

    private fun saveMovieDataToLocalBase(movieData: MovieDataTMDB) =
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.saveMovie(movieData)
        }

    /**
     * Method for getting movie details from a local database
     * @param movieData - The movie to get
     */

    private fun getMovieDetailsFromLocalDataBase(movieData: MovieDataTMDB) {
        if (movieData.id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = detailsRepository.getMovieFromLocalSource(movieData.id)
                _movieLocalData.postValue(LocalMovieAppState.Success(listOf(result)))
            }
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

    fun getSimilarMoviesFromRemoteSource(movieId: Int): Flow<PagingData<MovieDataTMDB>> =
        detailsRepository.getSimilarMovies(movieId)


}