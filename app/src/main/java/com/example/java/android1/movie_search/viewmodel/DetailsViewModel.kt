package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.java.android1.movie_search.app.App
import com.example.java.android1.movie_search.app.MovieDataAppState
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUEST_ERROR = "Server request error"
private const val CORRUPTED_DATA = "Incomplete data"

class DetailsViewModel(
    private val detailsRepository: DetailsRepository,
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {

    private val _detailsMovieData: MutableLiveData<MovieDataAppState> = MutableLiveData()
    val detailsMovieData: LiveData<MovieDataAppState> = _detailsMovieData
    private val _movieLocalData: MutableLiveData<RoomAppState> = MutableLiveData()
    val movieLocalData: LiveData<RoomAppState> = _movieLocalData

    private val callback = object : Callback<MovieDataTMDB> {
        override fun onResponse(call: Call<MovieDataTMDB>, response: Response<MovieDataTMDB>) {
            val serverResponse: MovieDataTMDB? = response.body()
            _detailsMovieData.value = if (response.isSuccessful && serverResponse != null) {
                getMovieDetailsFromLocalDataBase(serverResponse)
                checkResponse(serverResponse)
            } else {
                MovieDataAppState.Error(Throwable(REQUEST_ERROR))
            }
        }

        override fun onFailure(call: Call<MovieDataTMDB>, error: Throwable) {
            _detailsMovieData.value = MovieDataAppState.Error(error)
        }
    }

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

    fun getMovieDetailsFromRemoteSource(movieId: Int, language: String) {
        _detailsMovieData.value = MovieDataAppState.Loading
        detailsRepository.getMovieDetailsFromRemoteServer(movieId, language, callback)
    }

    /**
     * Method for saving a movie to a local database
     * @param movieData - The movie to save
     */

    private fun saveMovieDataToLocalBase(movieData: MovieDataTMDB) = viewModelScope.launch {
        _movieLocalData.value = RoomAppState.Loading
        movieLocalRepository.saveMovieToLocalDataBase(movieData)
    }

    /**
     * Method for getting movie details from a local database
     * @param movieData - The movie to get
     */

    fun getMovieDetailsFromLocalDataBase(movieData: MovieDataTMDB) {
        if (movieData.id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = movieLocalRepository.getMovieFromLocalDataBase(movieData.id)
                _movieLocalData.postValue(RoomAppState.Success(listOf(result)))
            }
        }
    }

    /**
     * Method for adding a movie to the list of favorite movies
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    fun setFavoriteMovie(movieId: Int, favorite: Boolean) = viewModelScope.launch {
        movieLocalRepository.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    fun getSimilarMoviesFromRemoteSource(movieId: Int): Flow<PagingData<MovieDataTMDB>> =
        detailsRepository.getSimilarMoviesFromRemoteServer(movieId)
}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(
                detailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
                movieLocalRepository = MovieLocalRepositoryImpl(App.movieDao)
            ) as T
        } else {
            throw IllegalArgumentException("DetailsViewModel not found")
        }
    }
}