package com.example.java.android1.movie_search.datasource.home

import com.example.java.android1.movie_search.model.old.remote.CategoryMoviesTMDB
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.convertMovieDtoToMovieEntity
import com.example.java.android1.movie_search.utils.convertMovieEntityToMovieDto
import com.example.java.android1.movie_search.utils.mapMovieEntityToLocalMovieData

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