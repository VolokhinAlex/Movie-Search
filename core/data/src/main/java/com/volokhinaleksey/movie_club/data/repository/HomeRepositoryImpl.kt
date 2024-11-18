package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class HomeRepositoryImpl(
    private val database: MovieDataBase,
    private val apiHolder: CoreApi
) : HomeRepository {

    override fun getMovies(
        categoryId: String
    ): Flow<List<Movie>> = database.moviesDao().getMoviesByCategory(categoryId)
        .map { it.map(MovieEntity::asExternalModel) }

    override fun getMovies(ids: List<Int>): Flow<List<Movie>> = database.moviesDao()
        .getMoviesByIds(ids)
        .map { it.map(MovieEntity::asExternalModel) }

    override suspend fun syncData(category: MovieCategory, lang: String) {
        val movies = when (category) {
            MovieCategory.Upcoming -> {
                val startReleaseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                apiHolder.moviesApi.getUpcomingMovies(
                    startReleaseDate = startReleaseDate,
                    language = lang
                ).results
            }
            else -> {
                apiHolder.moviesApi.getMoviesByCategory(
                    categoryId = category.id,
                    language = lang
                ).results
            }
        }

        val genres = apiHolder.moviesApi.getGenres(language = lang)

        database.genresDao().upsertAllGenres(genres.genres.map { it.asEntity() })

        database.moviesDao().insertAllMovies(movies.map { it.asEntity(category.id) })

        val entities = mutableListOf<MoviesGenresEntity>()

        for (movie in movies) {
            val movieId = movie.id ?: 0
            movie.genres?.forEach { genreId ->
                entities.add(MoviesGenresEntity(movieId = movieId, genreId = genreId))
            }
        }

        database.moviesDao().upsertMoviesGenres(entities)
    }
}