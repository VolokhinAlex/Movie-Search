package com.volokhinaleksey.movie_club.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.volokhinaleksey.movie_club.database.room.entity.MovieDetails
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.MoviesGenresEntity
import com.volokhinaleksey.movie_club.database.room.entity.SimilarMovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * The Interface for interacting with local movie table
 */

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(entity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovies(entity: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :categoryId")
    fun getMoviesByCategory(categoryId: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE category = :categoryId")
    fun getMoviesByCategoryPaging(categoryId: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    fun getMoviesByIds(ids: List<Int>): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieDetailsById(movieId: Int): MovieDetails?

    @Query(
        """
            SELECT * FROM movies
            INNER JOIN similar_movie sm ON id = sm.similar_id
            WHERE sm.movie_id = :movieId
        """
    )
    fun getSimilarMovies(movieId: Int): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM similar_movie WHERE movie_id = :movieId")
    suspend fun deleteSimilarMovies(movieId: Int)

    @Upsert
    suspend fun upsertMoviesGenres(moviesGenresEntities: List<MoviesGenresEntity>)

    @Upsert
    suspend fun upsertSimilarMovies(entities: List<SimilarMovieEntity>)
}