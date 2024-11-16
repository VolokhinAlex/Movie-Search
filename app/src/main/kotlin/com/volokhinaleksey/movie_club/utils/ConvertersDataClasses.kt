package com.volokhinaleksey.movie_club.utils

import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.local.LocalTrailerData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.ui.Actor
import com.volokhinaleksey.movie_club.model.ui.Genre
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.model.ui.Trailer

fun mapActorDtoToActorUI(actorDTO: ActorDTO): Actor {
    return Actor(
        actorId = actorDTO.id ?: 0,
        movieId = actorDTO.id ?: 0,
        biography = actorDTO.biography.orEmpty(),
        imdbId = actorDTO.imdbId.orEmpty(),
        birthday = actorDTO.birthday.orEmpty(),
        name = actorDTO.name.orEmpty(),
        placeOfBirth = actorDTO.placeOfBirth.orEmpty(),
        popularity = actorDTO.popularity ?: 0.0,
        profilePath = actorDTO.profilePath.orEmpty()
    )
}

fun mapLocalMovieToMovieUI(localMovieData: LocalMovieData): Movie {
    return Movie(
        adult = localMovieData.adult ?: false,
        backdropPath = localMovieData.backdropPath.orEmpty(),
        posterPath = localMovieData.moviePoster.orEmpty(),
        id = localMovieData.movieId ?: 0,
        imdbId = localMovieData.imdbId.orEmpty(),
        genres = localMovieData.genres.split(", ").map { Genre(id = 0, name = it) },
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

fun mapLocalActorDataToActorUI(localActorData: LocalActorData): Actor {
    return Actor(
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

fun mapActorUIToLocalActorData(actor: Actor): LocalActorData {
    return LocalActorData(
        actorId = actor.actorId,
        movieId = actor.movieId,
        biography = actor.biography,
        birthday = actor.birthday,
        imdbId = actor.imdbId,
        name = actor.name,
        placeOfBirth = actor.placeOfBirth,
        popularity = actor.popularity,
        profilePath = actor.profilePath,
        character = actor.character
    )
}

fun mapLocalMovieTrailerToTrailerUI(trailer: LocalTrailerData): Trailer {
    return Trailer(
        id = trailer.id.orEmpty(),
        name = trailer.name.orEmpty(),
        key = trailer.key.orEmpty(),
        type = trailer.type.orEmpty()
    )
}