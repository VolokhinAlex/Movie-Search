package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.utils.timeToFormatHoursAndMinutes

@Entity(tableName = "movies", indices = [Index("id")])
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster: String,
    @ColumnInfo("release_date") val releaseDate: String,
    val runtime: String,
    val overview: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    val language: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
)

fun Movie.asEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        poster = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        overview = overview,
        imdbId = imdbId,
        adult = adult,
        backdropPath = backdropPath,
        language = originalLanguage,
        voteAverage = voteAverage
    )
}

fun MovieDataTMDB.asEntity(): MovieEntity {
    return MovieEntity(
        id = id ?: 0,
        title = title.orEmpty(),
        poster = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime.timeToFormatHoursAndMinutes(),
        overview = overview.orEmpty(),
        imdbId = imdbId.orEmpty(),
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        language = originalLanguage.orEmpty(),
        voteAverage = voteAverage ?: 0.0
    )
}

fun MovieEntity.asExternalModel(categoryId: String = "") = Movie(
    adult = adult,
    backdropPath = backdropPath,
    posterPath = poster,
    id = id,
    imdbId = imdbId,
    originalLanguage = language,
    overview = overview,
    title = title,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    runtime = runtime,
    category = categoryId
)