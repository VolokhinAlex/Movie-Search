package com.example.java.android1.movie_search.viewmodel

import androidx.lifecycle.*
import com.example.java.android1.movie_search.model.CategoryData
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.repository.HomeRepository
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.app.MovieCategory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _homeLiveData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val homeLiveData: LiveData<CategoryAppState> = _homeLiveData

    private val _popularMutableLiveData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val popularMutableLiveData: LiveData<CategoryAppState> = _popularMutableLiveData

    private val _nowPlayingMutableLiveData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val nowPlayingMutableLiveData: LiveData<CategoryAppState> = _nowPlayingMutableLiveData

    private val _topRatedMutableLiveData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val topRatedMutableLiveData: LiveData<CategoryAppState> = _topRatedMutableLiveData

    private val _upcomingMutableLiveData: MutableLiveData<CategoryAppState> =
        MutableLiveData(CategoryAppState.Loading)
    val upcomingMutableLiveData: LiveData<CategoryAppState> = _upcomingMutableLiveData

    init {
        fetchAllCategoriesMovies()
    }

    private val callback = object : Callback<CategoryMoviesTMDB> {

        override fun onResponse(
            call: Call<CategoryMoviesTMDB>,
            response: Response<CategoryMoviesTMDB>
        ) {
            val serverResponse = response.body()
            _homeLiveData.value = if (response.isSuccessful && serverResponse != null) {
                val request = call.request().url.toString()
                val queryName = request.substring(request.indexOf("movie/"), request.indexOf("?"))
                CategoryAppState.Success(
                    CategoryData(
                        queryName.substring(queryName.indexOf("/") + 1),
                        serverResponse.results
                    )
                )
            } else {
                CategoryAppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<CategoryMoviesTMDB>, error: Throwable) {
            _homeLiveData.value = CategoryAppState.Error(error)
        }

    }

    fun getCategoryMovies(category: String, language: String, page: Int) {
        _homeLiveData.value = CategoryAppState.Loading
        repository.getMoviesCategoryFromRemoteServer(category, language, page, callback)
    }

    fun fetchAllCategoriesMovies() {
        getCategoryMovies(_popularMutableLiveData, MovieCategory.Popular.queryName, "en-EN", 1)
        getCategoryMovies(_nowPlayingMutableLiveData, MovieCategory.NowPlaying.queryName, "en-EN", 1)
        getCategoryMovies(_upcomingMutableLiveData, MovieCategory.Upcoming.queryName, "en-EN", 1)
        getCategoryMovies(_topRatedMutableLiveData, MovieCategory.TopRated.queryName, "en-EN", 1)
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

class MainViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("MainViewModel not found")
        }
    }
}