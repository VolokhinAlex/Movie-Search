package com.volokhinaleksey.movie_club.repository.category

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.datasource.category.CategoryDataSource
import com.volokhinaleksey.movie_club.datasource.category.LocalCategoryDataSource
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting category data from Remote Server
 */

// TODO (Refactor this)

class CategoryRepositoryImpl(
    private val remoteDataSource: CategoryDataSource<PagingData<MovieDataTMDB>>,
    private val localDataSource: LocalCategoryDataSource
) : CategoryRepository {

    /**
     * Method for getting category movies from the remote server
     * @param categoryMovies - The category of movies to get
     */

    override fun getCategoryMovies(
        categoryMovies: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<MovieUI>> {
//        return if (isNetworkAvailable) {
//            remoteDataSource.getCategoryMovies(categoryMovies = categoryMovies).map {
//                it.map { it.toMovieUI() }
//            }
//            remoteDataSource.getCategoryMovies(categoryMovies = categoryMovies).map {
//                it.map { movie ->
//                    localDataSource.saveMovie(
//                        mapMovieDataTMDBToLocalMovieData(
//                            movie,
//                            categoryMovies
//                        )
//                    )
//                    mapMovieDataTMDBToMovieUI(movie, categoryMovies)
//                }
//            }
//        } else {
//            localDataSource.getCategoryMovies(categoryMovies = categoryMovies).map {
//                it.map { movie ->
//                    localDataSource.saveMovie(movie)
//                    mapLocalMovieToMovieUI(movie)
//                }
//            }
//        }
        TODO()
    }

}
