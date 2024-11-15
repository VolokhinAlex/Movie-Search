package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.ui.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster: String,
    @ColumnInfo("release_date") val releaseDate: String,
    val runtime: String,
    val overview: String,
    val category: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    val language: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
)

fun Movie.asEntity(category: String): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        poster = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        overview = overview,
        category = category,
        imdbId = imdbId,
        adult = adult,
        backdropPath = backdropPath,
        language = originalLanguage,
        voteAverage = voteAverage
    )
}

fun MovieDataTMDB.asEntity(category: String): MovieEntity {
    return MovieEntity(
        id = id ?: 0,
        title = title.orEmpty(),
        poster = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        runtime = timeToFormatHoursAndMinutes(runtime ?: 0),
        overview = overview.orEmpty(),
        category = category,
        imdbId = imdb_id.orEmpty(),
        adult = adult ?: false,
        backdropPath = backdrop_path.orEmpty(),
        language = original_language.orEmpty(),
        voteAverage = vote_average ?: 0.0
    )
}

fun MovieEntity.asExternalModel() = Movie(
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
)

//TODO(utils module)
internal fun timeToFormatHoursAndMinutes(min: Int): String {
    val hour = min / 60
    val minutes = min % 60
    return String.format("%02dh %02dmin", hour, minutes)
}
