package com.volokhinaleksey.movie_club.data.mappers

import com.volokhinaleksey.movie_club.data.repository.timeToFormatHoursAndMinutes
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.remote.toTrailerUI
import com.volokhinaleksey.movie_club.model.ui.Genre
import com.volokhinaleksey.movie_club.model.ui.Movie

fun MovieDataTMDB.toMovieUI(
    category: String = "",
    isFavorite: Boolean = false
): Movie {
    return Movie(
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        posterPath = posterPath.orEmpty(),
        id = id ?: 0,
        imdbId = imdbId.orEmpty(),
        genres = genres?.map { Genre(id = it, name = "") } ?: emptyList(),
        originalLanguage = originalLanguage.orEmpty(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        releaseDate = releaseDate.orEmpty(),
        runtime = timeToFormatHoursAndMinutes(runtime ?: 0),
        actors = emptyList(),
        videos = videos?.results?.map { it.toTrailerUI() } ?: emptyList(),
        category = category,
        favorite = isFavorite
    )
}