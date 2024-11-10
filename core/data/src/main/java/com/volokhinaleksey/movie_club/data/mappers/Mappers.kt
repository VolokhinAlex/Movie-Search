package com.volokhinaleksey.movie_club.data.mappers

import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.remote.toActorUI
import com.volokhinaleksey.movie_club.model.remote.toGenreUI
import com.volokhinaleksey.movie_club.model.remote.toTrailerUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI

internal fun MovieDataTMDB.toMovieUI(category: String): MovieUI {
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
        runtime = runtime ?: 0,
        actors = credits?.cast?.map { it.toActorUI() } ?: emptyList(),
        videos = videos?.results?.map { it.toTrailerUI() } ?: emptyList(),
        category = category
    )
}