package com.volokhinaleksey.movie_club.data.repository

import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.database.room.entity.asExternalModel
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val database: MovieDataBase,
    private val apiHolder: CoreApi
) : HomeRepository {

    override fun getMovies(
        categoryId: String
    ): Flow<List<Movie>> = database.moviesDao().getMoviesByCategory(categoryId)
        .map { it.map(MovieEntity::asExternalModel) }

    override suspend fun syncData(categoryId: String, lang: String) {
        val movies = apiHolder.moviesApi.getMoviesByCategory(
            categoryId = categoryId,
            language = lang
        ).results

        val genres = apiHolder.moviesApi.getGenres(language = lang)

        database.genresDao().upsertAllGenres(genres.genres.map { it.asEntity() })

        database.moviesDao().upsertAllMovies(movies.map { it.asEntity(categoryId) })

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