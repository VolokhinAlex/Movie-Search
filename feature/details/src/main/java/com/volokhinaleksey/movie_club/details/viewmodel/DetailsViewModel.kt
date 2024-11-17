package com.volokhinaleksey.movie_club.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.volokhinaleksey.movie_club.domain.DetailsInteractor
import com.volokhinaleksey.movie_club.domain.LocaleInteractor
import com.volokhinaleksey.movie_club.model.state.DetailsMovieState
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsInteractor: DetailsInteractor,
    private val localeInteractor: LocaleInteractor
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<DetailsMovieState>(DetailsMovieState.Loading)
    val movieDetails get() = _movieDetails.asStateFlow()

    fun getMovieDetails(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                detailsInteractor.syncMovieDetails(
                    movieId = movie.id,
                    category = movie.category,
                    language = localeInteractor.getCurrentLanguage()
                )
                val result = detailsInteractor.getMovieDetails(movieId = movie.id)
                _movieDetails.emit(DetailsMovieState.Success(result))
                println("DetailsViewModel, result=$result")
            } catch (e: Exception) {
                _movieDetails.emit(DetailsMovieState.Error(e.localizedMessage?.toString() ?: ""))
            }
        }
    }

    /**
     * Method for adding a movie to the list of favorite movies
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    fun saveFavoriteMovie(movieId: Int, favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.saveFavoriteMovie(Favorite(movieId, favorite))
        }
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> =
        detailsInteractor
            .getSimilarMovies(movieId = movieId, language = localeInteractor.getCurrentLanguage())
            .cachedIn(viewModelScope)

}