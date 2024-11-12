package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.LocalSimilarPageSource
import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

//TODO("Refactor this")
class DetailsDatabaseRepositoryImpl(
    private val database: MoviesDataBase,
) : DetailsDatabaseRepository {

    override suspend fun saveMovieDetailsToLocalDataBase(movieUI: MovieUI) {
//        database.moviesDao().updateRuntimeAndGenres(
//            movieId = movieUI.id,
//            genres = movieUI.genres.joinToString { it.name },
//            runtime = movieUI.runtime
//        )
//        database.trailerDao().insert(movieUI.videos.map { it.asEntity(movieUI.id.toString()) })
//        database.actorDao().insert(movieUI.actors.map { it.asEntity() })
    }

    override suspend fun updateMovieFavoriteInLocalDataBase(movieId: Int, favorite: Boolean) {
//        database.moviesDao().updateFavorite(movieId = movieId, favorite = favorite)
    }

    override suspend fun saveSimilarMovies(movieUI: MovieUI, movieId: Int) {
//        database.similarMovieDao().insert(
//            SimilarMovieEntity(
//                similarMovieId = movieUI.id.toLong(),
//                movieId = movieId.toLong(),
//                name = movieUI.title
//            )
//        )
    }

    override suspend fun saveMovie(movieUI: MovieUI) {
//        database.moviesDao().insert(movieUI.asEntity(""))
    }

    override suspend fun getMovieDetails(movieId: Int, language: String): MovieUI {
        val movie = database.moviesDao().getMovieByMovieId(movieId = movieId)
        val trailers = database.trailerDao().getTrailerById(movieId = movieId)
        val actors = database.actorDao().getActorsByMovieId(movieId = movieId)
        return movie.toMovieUI(trailers, actors)
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LocalSimilarPageSource(db = database, movieId = movieId) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}