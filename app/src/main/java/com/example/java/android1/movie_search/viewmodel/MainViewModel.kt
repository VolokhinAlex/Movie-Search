package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.model.CategoryData
import com.example.java.android1.movie_search.repository.HomeRepository
import kotlinx.coroutines.launch

private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel(
    private val repository: HomeRepository
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

    fun fetchAllCategoriesMovies() {
        getCategoryMovies(_popularMoviesData, MovieCategory.Popular.queryName, "en-EN", 1)
        getCategoryMovies(_nowPlayingMoviesData, MovieCategory.NowPlaying.queryName, "en-EN", 1)
        getCategoryMovies(_upcomingMoviesData, MovieCategory.Upcoming.queryName, "en-EN", 1)
        getCategoryMovies(_topRatedMoviesData, MovieCategory.TopRated.queryName, "en-EN", 1)
    }

    private fun getCategoryMovies(
        livedata: MutableLiveData<CategoryAppState>,
        category: String,
        language: String,
        page: Int
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getCategoryMoviesFromRemoteServer(
                    category,
                    language,
                    page
                )
                val movieData = response.body()
                livedata.value =
                    if (response.isSuccessful && movieData != null) {
                        CategoryAppState.Success(
                            CategoryData(
                                category,
                                movieData.results
                            )
                        )
                    } else {
                        CategoryAppState.Error(Throwable(CORRUPTED_DATA))
                    }
            } catch (exception: Throwable) {
                livedata.value = CategoryAppState.Error(exception)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("MainViewModel not found")
        }
    }
}