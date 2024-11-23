package com.volokhinaleksey.movie_club.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import androidx.room.withTransaction
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.MovieCategoryEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.datastore.CategoryMoviesRemoteKey
import com.volokhinaleksey.movie_club.datastore.MovieClubDataStore
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CategoryMoviesRemoteMediator(
    private val category: String,
    private val language: String,
    private val database: MovieDataBase,
    private val datastore: MovieClubDataStore,
    private val coreApi: CoreApi,
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesDao = database.moviesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = datastore.getCategoryMoviesNextKey(category)
                val nextPage = remoteKey ?: return MediatorResult.Success(true)
                nextPage
            }
        }

        return try {
            // Иначе будут возвращаться неактуальные данные(за прошлые года)
            val result = when (category) {
                MovieCategory.Upcoming.id -> {
                    val startReleaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    coreApi.moviesApi.getUpcomingMovies(
                        startReleaseDate = startReleaseDate,
                        language = language,
                        page = page
                    ).results
                }
                else -> {
                    coreApi.moviesApi.getMoviesByCategory(
                        categoryId = category,
                        language = language,
                        page = page
                    ).results
                }
            }

            val endOfPaginationReached = result.isEmpty()
            val nextKey = if (endOfPaginationReached) null else page + 1

            datastore.saveCategoryMoviesRemoteKey(CategoryMoviesRemoteKey(category, nextKey ?: 1))

            saveCategoryMovies(result, "${category}_more")

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            println("$exception")
            return MediatorResult.Error(exception)
        } catch (e: Exception) {
            println("$e")
            return MediatorResult.Error(e)
        }
    }

    @Transaction
    suspend fun saveCategoryMovies(
        movies: List<MovieDataTMDB>,
        category: String
    ) {
        database.withTransaction {
            moviesDao.insertAllMovies(movies.map { it.asEntity() })
            database.movieCategoryDao()
                .insert(movies.map { MovieCategoryEntity(category, it.id ?: 0) })
            val entities = mutableListOf<MoviesGenresEntity>()
            for (movie in movies) {
                val movieId = movie.id ?: 0
                movie.genres?.forEach { genreId ->
                    entities.add(MoviesGenresEntity(movieId = movieId, genreId = genreId))
                }
            }
            moviesDao.upsertMoviesGenres(entities)
        }
    }
}