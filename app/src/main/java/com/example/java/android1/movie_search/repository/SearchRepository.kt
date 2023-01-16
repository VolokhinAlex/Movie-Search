package com.example.java.android1.movie_search.repository

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

/**
 * The remote repository to get a movie by name from TMDB API
 */

interface SearchRepository {

    fun getMoviesFromRemoteServer(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getSearchRequest(query: String): Flow<PagingData<MovieDataTMDB>>

}