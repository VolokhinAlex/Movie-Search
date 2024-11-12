package com.volokhinaleksey.movie_club.utils

import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.TrailerEntity
import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.local.LocalTrailerData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CastDTO
import com.volokhinaleksey.movie_club.model.remote.TrailerDTO
import com.volokhinaleksey.movie_club.model.ui.ActorUI
import com.volokhinaleksey.movie_club.model.ui.GenreUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
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

fun mapMovieEntityToLocalMovieData(
    movieEntity: MovieEntity,
    actors: List<ActorEntity>,
    trailers: List<TrailerEntity>
): LocalMovieData {
    return LocalMovieData(
        movieId = movieEntity.movieId,
        movieTitle = movieEntity.movieTitle,
        movieFavorite = movieEntity.movieFavorite,
        moviePoster = movieEntity.moviePoster,
        movieReleaseDate = movieEntity.movieReleaseDate,
        movieRating = movieEntity.movieRating,
        runtime = 0,
        genres = movieEntity.genres,
        overview = movieEntity.overview,
        video = trailers.map { mapTrailerEntityToLocalMovieTrailer(it) },
        actor = actors.map { mapActorEntityToLocalActorData(it) },
        category = movieEntity.category,
        imdbId = movieEntity.imdbId,
        adult = movieEntity.adult,
        backdropPath = movieEntity.backdropPath,
        originalLanguage = movieEntity.originalLanguage,
        voteCount = movieEntity.voteCount
    )
}

fun mapLocalMovieToMovieEntity(movie: LocalMovieData): MovieEntity {
    return MovieEntity(
        movieId = movie.movieId,
        movieTitle = movie.movieTitle,
        movieFavorite = movie.movieFavorite,
        moviePoster = movie.moviePoster,
        movieReleaseDate = movie.movieReleaseDate,
        movieRating = movie.movieRating,
        runtime = movie.runtime.toString(),
        genres = movie.genres,
        overview = movie.overview,
        category = movie.category,
        imdbId = movie.imdbId,
        adult = movie.adult,
        backdropPath = movie.backdropPath,
        originalLanguage = movie.originalLanguage,
        voteCount = movie.voteCount
    )
}

fun mapLocalMovieToMovieUI(localMovieData: LocalMovieData): MovieUI {
    return MovieUI(
        adult = localMovieData.adult ?: false,
        backdropPath = localMovieData.backdropPath.orEmpty(),
        posterPath = localMovieData.moviePoster.orEmpty(),
        id = localMovieData.movieId ?: 0,
        imdbId = localMovieData.imdbId.orEmpty(),
        genres = localMovieData.genres.split(", ").map { GenreUI(id = 0, name = it) },
        originalLanguage = localMovieData.originalLanguage.orEmpty(),
        overview = localMovieData.overview.orEmpty(),
        title = localMovieData.movieTitle.orEmpty(),
        voteCount = localMovieData.voteCount ?: 0,
        voteAverage = localMovieData.movieRating ?: 0.0,
        releaseDate = localMovieData.movieReleaseDate.orEmpty(),
        runtime = localMovieData.runtime.toString(),
        actors = localMovieData.actor.map { mapLocalActorDataToActorUI(it) },
        videos = localMovieData.video.map { mapLocalMovieTrailerToTrailerUI(it) },
        category = localMovieData.category,
        favorite = localMovieData.movieFavorite ?: false
    )
}

fun mapLocalActorDataToActorEntity(localActorData: LocalActorData): ActorEntity {
    return ActorEntity(
        actorId = localActorData.actorId,
        movieId = localActorData.movieId,
        biography = localActorData.biography,
        birthday = localActorData.birthday,
        imdbId = localActorData.imdbId,
        name = localActorData.name,
        placeOfBirth = localActorData.placeOfBirth,
        popularity = localActorData.popularity,
        profilePath = localActorData.profilePath,
        character = localActorData.character
    )
}

fun mapActorEntityToLocalActorData(actorEntity: ActorEntity): LocalActorData {
    return LocalActorData(
        actorId = actorEntity.actorId,
        movieId = actorEntity.movieId,
        biography = actorEntity.biography,
        birthday = actorEntity.birthday,
        imdbId = actorEntity.imdbId,
        name = actorEntity.name,
        placeOfBirth = actorEntity.placeOfBirth,
        popularity = actorEntity.popularity,
        profilePath = actorEntity.profilePath,
        character = actorEntity.character
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

fun mapTrailerEntityToLocalMovieTrailer(trailerEntity: TrailerEntity): LocalTrailerData {
    return LocalTrailerData(
        id = trailerEntity.id,
        name = trailerEntity.name,
        key = trailerEntity.key,
        type = trailerEntity.type
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


fun convertMovieEntityToListMovieDataRoom(entity: List<MovieEntity>): List<LocalMovieData> =
    entity.map {
        LocalMovieData(
            movieId = it.movieId,
            movieTitle = it.movieTitle,
            movieFavorite = it.movieFavorite,
            moviePoster = it.moviePoster,
            movieReleaseDate = it.movieReleaseDate,
            movieRating = it.movieRating,
            runtime = 0,
            genres = it.genres,
            overview = it.overview,
            video = emptyList(),
            actor = emptyList(),
            category = it.category,
            imdbId = it.imdbId,
            adult = it.adult,
            backdropPath = it.backdropPath,
            originalLanguage = it.originalLanguage,
            voteCount = it.voteCount
        )
    }