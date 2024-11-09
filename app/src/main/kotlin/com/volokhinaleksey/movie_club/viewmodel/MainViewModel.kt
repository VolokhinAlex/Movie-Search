package com.volokhinaleksey.movie_club.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.CategoryState
import com.volokhinaleksey.movie_club.repository.home.HomeRepository
import com.volokhinaleksey.movie_club.view.LanguageQuery
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _popularMoviesData: MutableLiveData<CategoryState> =
        MutableLiveData(CategoryState.Loading)
    val popularMoviesData: LiveData<CategoryState> = _popularMoviesData

    private val _nowPlayingMoviesData: MutableLiveData<CategoryState> =
        MutableLiveData(CategoryState.Loading)
    val nowPlayingMoviesData: LiveData<CategoryState> = _nowPlayingMoviesData

    private val _topRatedMoviesData: MutableLiveData<CategoryState> =
        MutableLiveData(CategoryState.Loading)
    val topRatedMoviesData: LiveData<CategoryState> = _topRatedMoviesData

    private val _upcomingMoviesData: MutableLiveData<CategoryState> =
        MutableLiveData(CategoryState.Loading)
    val upcomingMoviesData: LiveData<CategoryState> = _upcomingMoviesData

    /**
     * The method for getting all categories of movies.
     */

    fun fetchAllCategoriesMovies(isOnline: Boolean) {
        getCategoryMovies(
            categoryMoviesData = _popularMoviesData,
            category = MovieCategory.Popular.queryName,
            language = LanguageQuery.EN.languageQuery,
            isOnline = isOnline
        )
        getCategoryMovies(
            categoryMoviesData = _nowPlayingMoviesData,
            category = MovieCategory.NowPlaying.queryName,
            language = LanguageQuery.EN.languageQuery,
            isOnline = isOnline
        )
        getCategoryMovies(
            categoryMoviesData = _upcomingMoviesData,
            category = MovieCategory.Upcoming.queryName,
            language = LanguageQuery.EN.languageQuery,
            isOnline = isOnline
        )
        getCategoryMovies(
            categoryMoviesData = _topRatedMoviesData,
            category = MovieCategory.TopRated.queryName,
            language = LanguageQuery.EN.languageQuery,
            isOnline = isOnline
        )
    }

    /**
     * The method for getting the movie category.
     * @param categoryMoviesData - Mutable live data for category recording
     * @param category - Category to be requested. See [MovieCategory]
     * @param language - Language to display translated data for the fields that support it.
     */

    private fun getCategoryMovies(
        categoryMoviesData: MutableLiveData<CategoryState>,
        category: String,
        language: String,
        isOnline: Boolean
    ) {
        categoryMoviesData.value = CategoryState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            categoryMoviesData.postValue(CategoryState.Error(error))
        }) {
            val serverResponse = homeRepository.getCategoryMoviesFromRemoteServer(
                category = category,
                language = language,
                page = 1,
                isNetworkAvailable = isOnline
            )
            categoryMoviesData.postValue(serverResponse)
        }
    }
}
