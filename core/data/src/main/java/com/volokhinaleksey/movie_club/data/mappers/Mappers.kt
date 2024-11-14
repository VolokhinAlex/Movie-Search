package com.volokhinaleksey.movie_club.data.mappers

import com.volokhinaleksey.movie_club.data.repository.timeToFormatHoursAndMinutes
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.remote.toActorUI
import com.volokhinaleksey.movie_club.model.remote.toTrailerUI
import com.volokhinaleksey.movie_club.model.ui.Genre
import com.volokhinaleksey.movie_club.model.ui.Movie

fun MovieDataTMDB.toMovieUI(
    category: String = "",
    isFavorite: Boolean = false
): Movie {
    return Movie(
        adult = adult ?: false,
        backdropPath = backdrop_path.orEmpty(),
        posterPath = poster_path.orEmpty(),
        id = id ?: 0,
        imdbId = imdb_id.orEmpty(),
        genres = genres?.map { Genre(id = it, name = "") } ?: emptyList(),
        originalLanguage = original_language.orEmpty(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        voteAverage = vote_average ?: 0.0,
        releaseDate = release_date.orEmpty(),
        runtime = timeToFormatHoursAndMinutes(runtime ?: 0),
        actors = credits?.cast?.map { it.toActorUI() } ?: emptyList(),
        videos = videos?.results?.map { it.toTrailerUI() } ?: emptyList(),
        category = category,
        favorite = isFavorite
    )
}