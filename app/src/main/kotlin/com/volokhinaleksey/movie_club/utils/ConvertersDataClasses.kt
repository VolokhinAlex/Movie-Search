package com.volokhinaleksey.movie_club.utils

import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.local.LocalTrailerData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CastDTO
import com.volokhinaleksey.movie_club.model.remote.TrailerDTO
import com.volokhinaleksey.movie_club.model.ui.ActorUI
import com.volokhinaleksey.movie_club.model.ui.GenreUI
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.model.ui.TrailerUI

fun mapActorDtoToActorUI(actorDTO: ActorDTO): ActorUI {
    return ActorUI(
        actorId = actorDTO.id ?: 0,
        movieId = actorDTO.id ?: 0,
        biography = actorDTO.biography.orEmpty(),
        imdbId = actorDTO.imdb_id.orEmpty(),
        birthday = actorDTO.birthday.orEmpty(),
        name = actorDTO.name.orEmpty(),
        placeOfBirth = actorDTO.place_of_birth.orEmpty(),
        popularity = actorDTO.popularity ?: 0.0,
        profilePath = actorDTO.profile_path.orEmpty()
    )
}

fun mapTrailerDTOToLocalTrailerData(trailer: TrailerDTO): LocalTrailerData {
    return LocalTrailerData(
        id = trailer.id,
        name = trailer.name,
        key = trailer.key,
        type = trailer.type
    )
}

fun mapCastDtoToLocalActorData(actor: CastDTO, movieId: Long): LocalActorData {
    return LocalActorData(
        actorId = actor.id ?: 0,
        movieId = movieId,
        biography = null,
        birthday = null,
        imdbId = null,
        name = actor.name,
        placeOfBirth = null,
        popularity = null,
        profilePath = actor.profile_path,
        character = actor.character
    )
}

fun mapLocalMovieToMovieUI(localMovieData: LocalMovieData): Movie {
    return Movie(
        adult = localMovieData.adult ?: false,
        backdropPath = localMovieData.backdropPath.orEmpty(),
        posterPath = localMovieData.moviePoster.orEmpty(),
        id = localMovieData.movieId ?: 0,
        imdbId = localMovieData.imdbId.orEmpty(),
        genres = localMovieData.genres.split(", ").map { GenreUI(id = 0, name = it) },
        originalLanguage = localMovieData.originalLanguage.orEmpty(),
        overview = localMovieData.overview.orEmpty(),
        title = localMovieData.movieTitle.orEmpty(),
        voteAverage = localMovieData.movieRating ?: 0.0,
        releaseDate = localMovieData.movieReleaseDate.orEmpty(),
        runtime = localMovieData.runtime.toString(),
        actors = localMovieData.actor.map { mapLocalActorDataToActorUI(it) },
        videos = localMovieData.video.map { mapLocalMovieTrailerToTrailerUI(it) },
        category = localMovieData.category,
        favorite = localMovieData.movieFavorite ?: false
    )
}

fun mapLocalActorDataToActorUI(localActorData: LocalActorData): ActorUI {
    return ActorUI(
        actorId = localActorData.actorId,
        movieId = localActorData.movieId ?: 0,
        biography = localActorData.biography.orEmpty(),
        birthday = localActorData.birthday.orEmpty(),
        imdbId = localActorData.imdbId.orEmpty(),
        name = localActorData.name.orEmpty(),
        placeOfBirth = localActorData.placeOfBirth.orEmpty(),
        popularity = localActorData.popularity ?: 0.0,
        profilePath = localActorData.profilePath.orEmpty(),
        character = localActorData.character.orEmpty()
    )
}

fun mapActorUIToLocalActorData(actorUI: ActorUI): LocalActorData {
    return LocalActorData(
        actorId = actorUI.actorId,
        movieId = actorUI.movieId,
        biography = actorUI.biography,
        birthday = actorUI.birthday,
        imdbId = actorUI.imdbId,
        name = actorUI.name,
        placeOfBirth = actorUI.placeOfBirth,
        popularity = actorUI.popularity,
        profilePath = actorUI.profilePath,
        character = actorUI.character
    )
}

fun mapLocalMovieTrailerToTrailerUI(trailer: LocalTrailerData): TrailerUI {
    return TrailerUI(
        id = trailer.id.orEmpty(),
        name = trailer.name.orEmpty(),
        key = trailer.key.orEmpty(),
        type = trailer.type.orEmpty()
    )
}