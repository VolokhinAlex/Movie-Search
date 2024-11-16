package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.RemoteSimilarPageSource
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow

class DetailsRepositoryImpl(
    private val apiHolder: CoreApi,
    private val dataBase: MovieDataBase,
) : DetailsRepository {
    override suspend fun getMovieDetails(movieId: Int) =
        dataBase.moviesDao().getMovieDetailsById(movieId)?.asExternalModel() ?: Movie()

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RemoteSimilarPageSource(
                    apiHolder = apiHolder,
                    movieId = movieId
                )
            }
        ).flow
    }

    override suspend fun saveFavoriteMovie(favorite: Favorite) {
        dataBase.favoritesDao().saveFavoriteMovie(favorite.asEntity())
    }

    override suspend fun syncMovieDetails(movieId: Int, language: String) {
        val result = apiHolder.moviesApi.getMovieDetails(
            movieId = movieId,
            language = language,
            extraRequests = "credits,videos"
        )

        result.videos?.results?.map { it.asEntity(movieId) }?.also {
            dataBase.trailersDao().insertTrailers(it)
        }

        result.credits?.cast?.map { it.asEntity(movieId) }?.also {
            dataBase.actorsDao().upsertActors(it)
        }

        dataBase.moviesDao().upsertMovie(result.asEntity())
    }
}