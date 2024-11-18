package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.volokhinaleksey.movie_club.data.mediator.CategoryMoviesRemoteMediator
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.datastore.MovieClubDataStore
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CategoryRepository {
    fun getCategoryMovies(category: String, language: String): Flow<PagingData<Movie>>
}

class CategoryRepositoryImpl(
    private val coreApi: CoreApi,
    private val database: MovieDataBase,
    private val datastore: MovieClubDataStore,
) : CategoryRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCategoryMovies(category: String, language: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = CategoryMoviesRemoteMediator(
            category = category,
            language = language,
            database = database,
            datastore = datastore,
            coreApi = coreApi
        ),
        pagingSourceFactory = { database.moviesDao().getMoviesByCategoryPaging("${category}_more") }
    ).flow.map { movies -> movies.map { it.asExternalModel() } }
}