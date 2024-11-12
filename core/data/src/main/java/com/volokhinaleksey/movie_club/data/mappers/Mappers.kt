package com.volokhinaleksey.movie_club.data.mappers

import com.volokhinaleksey.movie_club.data.repository.timeToFormatHoursAndMinutes
import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.model.local.LocalTrailerData
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.remote.toActorUI
import com.volokhinaleksey.movie_club.model.remote.toGenreUI
import com.volokhinaleksey.movie_club.model.remote.toTrailerUI
import com.volokhinaleksey.movie_club.model.ui.ActorUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.model.ui.TrailerUI

fun MovieDataTMDB.toMovieUI(
    category: String = "",
    isFavorite: Boolean = false
): MovieUI {
    return MovieUI(
        adult = adult ?: false,
        backdropPath = backdrop_path.orEmpty(),
        posterPath = poster_path.orEmpty(),
        budget = budget ?: 0,
        id = id ?: 0,
        imdbId = imdb_id.orEmpty(),
        genres = genres?.map { it.toGenreUI() } ?: emptyList(),
        originalLanguage = original_language.orEmpty(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        voteCount = vote_count ?: 0,
        voteAverage = vote_average ?: 0.0,
        releaseDate = release_date.orEmpty(),
        runtime = timeToFormatHoursAndMinutes(runtime ?: 0),
        actors = credits?.cast?.map { it.toActorUI() } ?: emptyList(),
        videos = videos?.results?.map { it.toTrailerUI() } ?: emptyList(),
        category = category,
        favorite = isFavorite
    )
}

internal fun LocalActorData.toActorUI(): ActorUI {
    return ActorUI(
        actorId = actorId,
        movieId = movieId ?: 0,
        biography = biography.orEmpty(),
        birthday = birthday.orEmpty(),
        imdbId = imdbId.orEmpty(),
        name = name.orEmpty(),
        placeOfBirth = placeOfBirth.orEmpty(),
        popularity = popularity ?: 0.0,
        profilePath = profilePath.orEmpty(),
        character = character.orEmpty()
    )
}

fun LocalTrailerData.toTrailerUI(): TrailerUI {
    return TrailerUI(
        id = id.orEmpty(),
        name = name.orEmpty(),
        key = key.orEmpty(),
        type = type.orEmpty()
    )
}