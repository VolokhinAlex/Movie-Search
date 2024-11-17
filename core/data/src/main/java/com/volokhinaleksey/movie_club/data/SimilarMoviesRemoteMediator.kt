package com.volokhinaleksey.movie_club.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import androidx.room.withTransaction
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieRemoteKey
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class SimilarMoviesRemoteMediator(
    private val movieId: Int,
    private val lang: String,
    private val apiHolder: CoreApi,
    private val database: MovieDataBase,
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesDao = database.moviesDao()
    private val similarMoviesRemoteKeyDao = database.similarMoviesRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = database.withTransaction {
                    similarMoviesRemoteKeyDao.getKeyByMovieId(movieId)
                }

                val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextPage
            }
        }

        return try {
            val serverResponse = apiHolder.moviesApi.getSimilarMovies(
                movieId = movieId,
                language = lang,
                page = page,
            ).results.map { it.asEntity("similar") }

            val endOfPaginationReached = serverResponse.isEmpty()
            val nextKey = if (endOfPaginationReached) null else page + 1
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteSimilarMovies(movieId)
                    similarMoviesRemoteKeyDao.deleteByMovieId(movieId)
                }
                val remoteKey = SimilarMovieRemoteKey(movieId = movieId, nextPage = nextKey)
                val similarMovieEntities = serverResponse.map { SimilarMovieEntity(it.id, movieId) }
                saveSimilarMovies(
                    similarMovieRemoteKey = remoteKey,
                    movies = serverResponse,
                    similarMovies = similarMovieEntities
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    @Transaction
    suspend fun saveSimilarMovies(
        similarMovieRemoteKey: SimilarMovieRemoteKey,
        movies: List<MovieEntity>,
        similarMovies: List<SimilarMovieEntity>,
    ) {
        similarMoviesRemoteKeyDao.insertRemoteKey(similarMovieRemoteKey)
        moviesDao.insertAllMovies(movies)
        moviesDao.upsertSimilarMovies(similarMovies)
    }
}