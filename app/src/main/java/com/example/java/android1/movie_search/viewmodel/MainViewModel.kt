package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.model.CategoryMoviesData
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.view.LanguageQuery
import kotlinx.coroutines.launch

private const val CORRUPTED_DATA = "Неполные данные"

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
            LanguageQuery.EN.languageQuery,
            1
        )
        getCategoryMovies(
            _nowPlayingMoviesData,
            MovieCategory.NowPlaying.queryName,
            LanguageQuery.EN.languageQuery,
            1
        )
        getCategoryMovies(
            _upcomingMoviesData,
            MovieCategory.Upcoming.queryName,
            LanguageQuery.EN.languageQuery,
            1
        )
        getCategoryMovies(
            _topRatedMoviesData,
            MovieCategory.TopRated.queryName,
            LanguageQuery.EN.languageQuery,
            1
        )
    }

    /**
     * The method for getting the movie category.
     * @param categoryMoviesData - Mutable live data for category recording
     * @param category - Category to be requested. See [MovieCategory]
     * @param language - Language to display translated data for the fields that support it.
     * @param page - The page to be requested
     */

    private fun getCategoryMovies(
        categoryMoviesData: MutableLiveData<CategoryAppState>,
        category: String,
        language: String,
        page: Int
    ) {
        viewModelScope.launch {
            try {
                val serverResponse = homeRepository.getCategoryMoviesFromRemoteServer(
                    category,
                    language,
                    page
                )
                val movieData = serverResponse.body()
                categoryMoviesData.value =
                    if (serverResponse.isSuccessful && movieData != null) {
                        CategoryAppState.Success(
                            CategoryMoviesData(
                                category,
                                movieData.results
                            )
                        )
                    } else {
                        CategoryAppState.Error(Throwable(CORRUPTED_DATA))
                    }
            } catch (exception: Throwable) {
                categoryMoviesData.value = CategoryAppState.Error(exception)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(homeRepository = HomeRepositoryImpl(RemoteDataSource())) as T
        } else {
            throw IllegalArgumentException("MainViewModel not found")
        }
    }
}