package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.volokhinaleksey.movie_club.data.mediator.SimilarMoviesRemoteMediator
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.map

class DetailsRepositoryImpl(
    private val apiHolder: CoreApi,
    private val dataBase: MovieDataBase,
) : DetailsRepository {
    override suspend fun getMovieDetails(movieId: Int) =
        dataBase.moviesDao().getMovieDetailsById(movieId)?.asExternalModel() ?: Movie()

    @OptIn(ExperimentalPagingApi::class)
    override fun getSimilarMovies(movieId: Int, language: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = SimilarMoviesRemoteMediator(
            movieId = movieId,
            lang = language,
            apiHolder = apiHolder,
            database = dataBase,
        ),
        pagingSourceFactory = { dataBase.moviesDao().getSimilarMovies(movieId) }
    ).flow.map { movies -> movies.map { it.asExternalModel() } }

    override suspend fun saveFavoriteMovie(favorite: Favorite) {
        dataBase.favoritesDao().saveFavoriteMovie(favorite.asEntity())
    }

    override suspend fun syncMovieDetails(movieId: Int, category: String, language: String) {
        val result = apiHolder.moviesApi.getMovieDetails(
            movieId = movieId,
            language = language,
            extraRequests = "credits,videos"
        )

        result.videos?.results?.map { it.asEntity(movieId) }?.also {
            dataBase.trailersDao().insertTrailers(it)
        }

        result.credits?.cast?.map { it.asEntity(movieId) }?.also {
            dataBase.actorsDao().insertActors(it)
        }

        dataBase.moviesDao().upsertMovie(result.asEntity(category))
    }
}