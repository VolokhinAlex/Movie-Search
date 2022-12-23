package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

interface SearchRepository {

    fun getMoviesFromServer(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    )

}