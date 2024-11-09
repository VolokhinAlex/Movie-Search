package com.volokhinaleksey.movie_club.datasource.home

import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.room.MoviesDataBase
import com.volokhinaleksey.movie_club.utils.convertMovieDtoToMovieEntity
import com.volokhinaleksey.movie_club.utils.convertMovieEntityToMovieDto
import com.volokhinaleksey.movie_club.utils.mapMovieEntityToLocalMovieData

class LocalHomeDataSourceImpl(
    private val db: MoviesDataBase
) : LocalHomeDataSource {
    override suspend fun saveMovie(moviesTMDB: CategoryMoviesTMDB, category: String) {
        db.moviesDao().insert(moviesTMDB.results.map { convertMovieDtoToMovieEntity(it, category) })
    }

    override suspend fun getMovies(
        category: String,
        language: String,
        page: Int
    ): CategoryMoviesTMDB {
        return CategoryMoviesTMDB(results = db.moviesDao().getMoviesByCategory(category).map {
            val trailers = db.trailerDao().getTrailerById(it.movieId ?: 0)
            val actors = db.actorDao().getActorsByMovieId(it.movieId ?: 0)
            convertMovieEntityToMovieDto(
                movie = mapMovieEntityToLocalMovieData(
                    movieEntity = it,
                    trailers = trailers,
                    actors = actors
                ),
                actors = actors,
                trailer = trailers
            )
        })
    }

}