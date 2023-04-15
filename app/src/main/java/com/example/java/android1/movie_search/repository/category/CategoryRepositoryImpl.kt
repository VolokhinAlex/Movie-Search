package com.example.java.android1.movie_search.repository.category

import androidx.paging.PagingData
import androidx.paging.map
import com.example.java.android1.movie_search.datasource.category.CategoryDataSource
import com.example.java.android1.movie_search.datasource.category.LocalCategoryDataSource
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.utils.mapMovieDataTMDBToLocalMovieData
import com.example.java.android1.movie_search.utils.mapMovieDataTMDBToMovieUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the interface for getting category data from Remote Server
 */

class CategoryRepositoryImpl(
    private val remoteDataSource: CategoryDataSource<PagingData<MovieDataTMDB>>,
    private val localDataSource: LocalCategoryDataSource
) : CategoryRepository {

    /**
     * Method for getting category movies from the remote server
     * @param categoryMovies - The category of movies to get
     */

    override fun getCategoryMovies(categoryMovies: String): Flow<PagingData<MovieUI>> {
        return remoteDataSource.getCategoryMovies(categoryMovies = categoryMovies).map {
            it.map { movie ->
                localDataSource.saveMovie(mapMovieDataTMDBToLocalMovieData(movie, categoryMovies))
                mapMovieDataTMDBToMovieUI(movie, categoryMovies)
            }
        }
    }
}
