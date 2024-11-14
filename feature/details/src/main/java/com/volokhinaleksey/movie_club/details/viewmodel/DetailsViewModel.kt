package com.volokhinaleksey.movie_club.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.volokhinaleksey.movie_club.domain.DetailsInteractor
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    private val _movieDetails = MutableSharedFlow<MovieState>()
    val movieDetails get() = _movieDetails.asSharedFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movieDetails.emit(MovieState.Loading)
                val result = detailsInteractor.getMovieDetails(
                    movieId = movieId,
                    language = "en-EN",
                    isNetworkAvailable = true
                )
                _movieDetails.emit(result)
            } catch (e: Exception) {
                _movieDetails.emit(MovieState.Error(e))
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
            detailsInteractor.updateMovie(movieId, favorite)
        }
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> =
        detailsInteractor.getSimilarMovies(movieId = movieId, isNetworkAvailable = true)
            .cachedIn(viewModelScope)


}