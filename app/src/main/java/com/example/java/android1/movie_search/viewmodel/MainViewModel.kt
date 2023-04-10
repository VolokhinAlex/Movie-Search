package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.model.CategoryMoviesData
import com.example.java.android1.movie_search.model.states.CategoryAppState
import com.example.java.android1.movie_search.repository.home.HomeRepository
import com.example.java.android1.movie_search.view.LanguageQuery
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _popularMoviesData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val popularMoviesData: LiveData<CategoryAppState> = _popularMoviesData

    private val _nowPlayingMoviesData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val nowPlayingMoviesData: LiveData<CategoryAppState> = _nowPlayingMoviesData

    private val _topRatedMoviesData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val topRatedMoviesData: LiveData<CategoryAppState> = _topRatedMoviesData

    private val _upcomingMoviesData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val upcomingMoviesData: LiveData<CategoryAppState> = _upcomingMoviesData

    init {
        fetchAllCategoriesMovies()
    }

    /**
     * The method for getting all categories of movies.
     */

    fun fetchAllCategoriesMovies() {
        getCategoryMovies(
            _popularMoviesData,
            MovieCategory.Popular.queryName,
            LanguageQuery.EN.languageQuery
        )
        getCategoryMovies(
            _nowPlayingMoviesData,
            MovieCategory.NowPlaying.queryName,
            LanguageQuery.EN.languageQuery
        )
        getCategoryMovies(
            _upcomingMoviesData,
            MovieCategory.Upcoming.queryName,
            LanguageQuery.EN.languageQuery
        )
        getCategoryMovies(
            _topRatedMoviesData,
            MovieCategory.TopRated.queryName,
            LanguageQuery.EN.languageQuery
        )
    }

    /**
     * The method for getting the movie category.
     * @param categoryMoviesData - Mutable live data for category recording
     * @param category - Category to be requested. See [MovieCategory]
     * @param language - Language to display translated data for the fields that support it.
     */

    private fun getCategoryMovies(
        categoryMoviesData: MutableLiveData<CategoryAppState>,
        category: String,
        language: String
    ) {
        categoryMoviesData.value = CategoryAppState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            categoryMoviesData.postValue(CategoryAppState.Error(error))
        }) {
            val serverResponse = homeRepository.getCategoryMoviesFromRemoteServer(
                category,
                language,
                page = 1,
                isNetworkAvailable = true
            )
            categoryMoviesData.postValue(
                CategoryAppState.Success(
                    CategoryMoviesData(
                        category,
                        serverResponse.results
                    )
                )
            )
        }
    }
}
