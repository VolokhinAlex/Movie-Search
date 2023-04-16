package com.example.java.android1.movie_search.datasource.category

import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieEntity
import com.example.java.android1.movie_search.utils.mapMovieEntityToLocalMovieData

class LocalCategoryDataSourceImpl(
    private val db: MoviesDataBase
) : LocalCategoryDataSource {
    override suspend fun saveMovie(movie: LocalMovieData) {
        db.moviesDao().insert(mapLocalMovieToMovieEntity(movie))
    }

    override suspend fun getMoviesByCategory(category: String): List<LocalMovieData> {
        return db.moviesDao().getMoviesByCategory(category = category).map {
            mapMovieEntityToLocalMovieData(
                movieEntity = it,
                actors = emptyList(),
                trailers = emptyList()
            )
        }
    }

}