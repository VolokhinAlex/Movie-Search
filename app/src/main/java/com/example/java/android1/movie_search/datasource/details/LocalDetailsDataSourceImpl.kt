package com.example.java.android1.movie_search.datasource.details

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.pagesource.LocalSimilarPageSource
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.model.local.SimilarMovieEntity
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapLocalActorDataToActorEntity
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieEntity
import com.example.java.android1.movie_search.utils.mapLocalMovieTrailerToTrailerEntity
import com.example.java.android1.movie_search.utils.mapMovieEntityToLocalMovieData
import kotlinx.coroutines.flow.Flow

class LocalDetailsDataSourceImpl(
    private val db: MoviesDataBase
) : LocalDetailsDataSource {

    /**
     * Method for saving a movie to a local database
     * @param localMovieData - The movie to save
     */

    override suspend fun saveMovieDetailsToLocalDataBase(localMovieData: LocalMovieData) {
        db.moviesDao().updateRuntimeAndGenres(
            movieId = localMovieData.movieId ?: 0,
            genres = localMovieData.genres,
            runtime = localMovieData.runtime ?: 0
        )
        db.trailerDao()
            .insert(localMovieData.video.map {
                mapLocalMovieTrailerToTrailerEntity(
                    localTrailerData = it,
                    movieId = localMovieData.movieId.toString()
                )
            })
        db.actorDao()
            .insert(localMovieData.actor.map { mapLocalActorDataToActorEntity(it) })
    }

    /**
     * Method for adding or removing a movie from the favorites list
     * @param movieId - The movie id to add
     * @param favorite - If we add then true if we delete then false
     */

    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
        db.moviesDao().updateFavorite(movieId = movieId, favorite = favorite)
    }

    override suspend fun saveSimilarMovies(localMovieData: LocalMovieData, movieId: Int) {
        db.similarMovieDao().insert(
            SimilarMovieEntity(
                similarMovieId = localMovieData.movieId?.toLong() ?: 0,
                movieId = movieId.toLong(),
                name = localMovieData.movieTitle.orEmpty()
            )
        )
    }

    override suspend fun saveMovie(localMovieData: LocalMovieData) {
        db.moviesDao().insert(mapLocalMovieToMovieEntity(localMovieData))
    }

    override suspend fun getMovieDetails(movieId: Int, language: String): LocalMovieData {
        return mapMovieEntityToLocalMovieData(
            movieEntity = db.moviesDao().getMovieByMovieId(movieId = movieId),
            trailers = db.trailerDao().getTrailerById(movieId = movieId),
            actors = db.actorDao().getActorsByMovieId(movieId = movieId)
        )
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<LocalMovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalSimilarPageSource(
                    db = db,
                    movieId = movieId
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}