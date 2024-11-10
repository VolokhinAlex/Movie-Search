package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.ui.GenreUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI

/**
 * The model of local movie table
 * @param movieId - movie ID
 * @param movieTitle - The title of a particular movie
 * @param movieFavorite - Favorite movie. Values 1 (True) or 0 (False)
 * @param moviePoster - Movie Image
 * @param movieReleaseDate - Release date of the movie
 * @param movieRating - Rating of the movie from 0 to 10
 */

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int?,
    @ColumnInfo(name = "movie_title")
    val movieTitle: String?,
    @ColumnInfo(name = "movie_favorite")
    val movieFavorite: Boolean?,
    @ColumnInfo(name = "movie_poster")
    val moviePoster: String?,
    @ColumnInfo(name = "movie_release_date")
    val movieReleaseDate: String?,
    @ColumnInfo(name = "movie_rating")
    val movieRating: Double?,
    val runtime: Int?,
    val genres: String,
    val overview: String?,
    val category: String,
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,
)

fun MovieUI.asEntity(categoryId: String): MovieEntity {
    return MovieEntity(
        movieId = id,
        movieTitle = title,
        movieFavorite = false,
        moviePoster = posterPath,
        movieReleaseDate = releaseDate,
        movieRating = voteAverage,
        runtime = runtime,
        genres = genres.joinToString { it.name },
        overview = overview,
        category = categoryId,
        adult = adult,
        imdbId = imdbId,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        voteCount = voteCount
    )
}

fun MovieEntity.toMovieUI(
    trailers: List<TrailerEntity>,
    actors: List<ActorEntity>,
): MovieUI {
    return MovieUI(
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        posterPath = moviePoster.orEmpty(),
        budget = 0,
        id = movieId ?: 0,
        imdbId = imdbId.orEmpty(),
        genres = genres.split(",").map { GenreUI(id = 0, name = it) },
        originalLanguage = originalLanguage.orEmpty(),
        overview = overview.orEmpty(),
        title = movieTitle.orEmpty(),
        voteCount = voteCount ?: 0,
        voteAverage = movieRating ?: 0.0,
        releaseDate = movieReleaseDate.orEmpty(),
        runtime = runtime ?: 0,
        favorite = movieFavorite ?: false,
        actors = actors.map { it.toActorUI() },
        videos = trailers.map { it.toTrailerUI() }
    )
}
