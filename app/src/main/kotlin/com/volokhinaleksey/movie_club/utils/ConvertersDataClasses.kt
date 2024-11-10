package com.volokhinaleksey.movie_club.utils

import com.volokhinaleksey.movie_club.database.room.entity.ActorEntity
import com.volokhinaleksey.movie_club.database.room.entity.MovieEntity
import com.volokhinaleksey.movie_club.database.room.entity.TrailerEntity
import com.volokhinaleksey.movie_club.model.local.LocalActorData
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.local.LocalTrailerData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CastDTO
import com.volokhinaleksey.movie_club.model.remote.CreditsDTO
import com.volokhinaleksey.movie_club.model.remote.GenresDTO
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.remote.TrailerDTO
import com.volokhinaleksey.movie_club.model.ui.ActorUI
import com.volokhinaleksey.movie_club.model.ui.GenreUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.model.ui.TrailerUI

fun mapMovieDataTMDBToMovieUI(moviesTMDB: MovieDataTMDB, category: String): MovieUI {
    return MovieUI(
        adult = moviesTMDB.adult ?: false,
        backdropPath = moviesTMDB.backdrop_path.orEmpty(),
        posterPath = moviesTMDB.poster_path.orEmpty(),
        budget = moviesTMDB.budget ?: 0,
        id = moviesTMDB.id ?: 0,
        imdbId = moviesTMDB.imdb_id.orEmpty(),
        genres = moviesTMDB.genres?.map { mapGenreDtoToGenreUI(it) } ?: emptyList(),
        originalLanguage = moviesTMDB.original_language.orEmpty(),
        overview = moviesTMDB.overview.orEmpty(),
        title = moviesTMDB.title.orEmpty(),
        voteCount = moviesTMDB.vote_count ?: 0,
        voteAverage = moviesTMDB.vote_average ?: 0.0,
        releaseDate = moviesTMDB.release_date.orEmpty(),
        runtime = moviesTMDB.runtime ?: 0,
        actors = moviesTMDB.credits?.cast?.map { mapCastDtoToActorUI(it) } ?: emptyList(),
        videos = moviesTMDB.videos?.results?.map { mapTrailerDtoToTrailerUI(it) } ?: emptyList(),
        category = category
    )
}

fun mapMovieDataTMDBToMovieUI(
    moviesTMDB: MovieDataTMDB,
    category: String,
    favorite: Boolean
): MovieUI {
    return MovieUI(
        adult = moviesTMDB.adult ?: false,
        backdropPath = moviesTMDB.backdrop_path.orEmpty(),
        posterPath = moviesTMDB.poster_path.orEmpty(),
        budget = moviesTMDB.budget ?: 0,
        id = moviesTMDB.id ?: 0,
        imdbId = moviesTMDB.imdb_id.orEmpty(),
        genres = moviesTMDB.genres?.map { mapGenreDtoToGenreUI(it) } ?: emptyList(),
        originalLanguage = moviesTMDB.original_language.orEmpty(),
        overview = moviesTMDB.overview.orEmpty(),
        title = moviesTMDB.title.orEmpty(),
        voteCount = moviesTMDB.vote_count ?: 0,
        voteAverage = moviesTMDB.vote_average ?: 0.0,
        releaseDate = moviesTMDB.release_date.orEmpty(),
        runtime = moviesTMDB.runtime ?: 0,
        actors = moviesTMDB.credits?.cast?.map { mapCastDtoToActorUI(it) } ?: emptyList(),
        videos = moviesTMDB.videos?.results?.map { mapTrailerDtoToTrailerUI(it) } ?: emptyList(),
        category = category,
        favorite = favorite
    )
}

fun mapMovieDataTMDBToMovieUI(moviesTMDB: MovieDataTMDB): MovieUI {
    return MovieUI(
        adult = moviesTMDB.adult ?: false,
        backdropPath = moviesTMDB.backdrop_path.orEmpty(),
        posterPath = moviesTMDB.poster_path.orEmpty(),
        budget = moviesTMDB.budget ?: 0,
        id = moviesTMDB.id ?: 0,
        imdbId = moviesTMDB.imdb_id.orEmpty(),
        genres = moviesTMDB.genres?.map { mapGenreDtoToGenreUI(it) } ?: emptyList(),
        originalLanguage = moviesTMDB.original_language.orEmpty(),
        overview = moviesTMDB.overview.orEmpty(),
        title = moviesTMDB.title.orEmpty(),
        voteCount = moviesTMDB.vote_count ?: 0,
        voteAverage = moviesTMDB.vote_average ?: 0.0,
        releaseDate = moviesTMDB.release_date.orEmpty(),
        runtime = moviesTMDB.runtime ?: 0,
        actors = moviesTMDB.credits?.cast?.map { mapCastDtoToActorUI(it) } ?: emptyList(),
        videos = moviesTMDB.videos?.results?.map { mapTrailerDtoToTrailerUI(it) } ?: emptyList(),
        category = "search"
    )
}

fun mapGenreDtoToGenreUI(genresDTO: GenresDTO): GenreUI {
    return GenreUI(
        id = genresDTO.id ?: 0,
        name = genresDTO.name.orEmpty()
    )
}

fun mapCastDtoToActorUI(castDTO: CastDTO): ActorUI {
    return ActorUI(
        actorId = castDTO.id ?: 0,
        name = castDTO.name.orEmpty(),
        character = castDTO.character.orEmpty(),
        profilePath = castDTO.profile_path.orEmpty()
    )
}

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

fun mapTrailerDtoToTrailerUI(trailerDTO: TrailerDTO): TrailerUI {
    return TrailerUI(
        id = trailerDTO.id.orEmpty(),
        name = trailerDTO.name.orEmpty(),
        key = trailerDTO.key.orEmpty(),
        type = trailerDTO.type.orEmpty()
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

fun mapActorDTOToLocalActorData(actor: ActorDTO, movieId: Long): LocalActorData {
    return LocalActorData(
        actorId = actor.id ?: 0,
        movieId = movieId,
        biography = actor.biography,
        birthday = actor.birthday,
        imdbId = actor.imdb_id,
        name = actor.name,
        placeOfBirth = actor.place_of_birth,
        popularity = actor.popularity,
        profilePath = actor.profile_path,
        character = ""
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
        runtime = movieEntity.runtime,
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
        runtime = movie.runtime,
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

fun mapActorEntityToActorUI(actorEntity: ActorEntity): ActorUI {
    return ActorUI(
        actorId = actorEntity.actorId,
        movieId = actorEntity.movieId ?: 0,
        birthday = actorEntity.birthday.orEmpty(),
        biography = actorEntity.biography.orEmpty(),
        imdbId = actorEntity.imdbId.orEmpty(),
        name = actorEntity.name.orEmpty(),
        placeOfBirth = actorEntity.placeOfBirth.orEmpty(),
        popularity = actorEntity.popularity ?: 0.0,
        profilePath = actorEntity.profilePath.orEmpty(),
        character = actorEntity.character.orEmpty()
    )
}

fun mapTrailerEntityToTrailerUI(trailerEntity: TrailerEntity): TrailerUI {
    return TrailerUI(
        id = trailerEntity.id,
        name = trailerEntity.name,
        key = trailerEntity.key.orEmpty(),
        type = trailerEntity.type.orEmpty()
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
        runtime = localMovieData.runtime ?: 0,
        actors = localMovieData.actor.map { mapLocalActorDataToActorUI(it) },
        videos = localMovieData.video.map { mapLocalMovieTrailerToTrailerUI(it) },
        category = localMovieData.category,
        favorite = localMovieData.movieFavorite ?: false
    )
}

fun mapMovieDataTMDBToLocalMovieData(movieTMDB: MovieDataTMDB): LocalMovieData {
    return LocalMovieData(
        movieId = movieTMDB.id,
        movieTitle = movieTMDB.title,
        movieFavorite = false,
        moviePoster = movieTMDB.poster_path,
        movieReleaseDate = movieTMDB.release_date,
        movieRating = movieTMDB.vote_average,
        runtime = movieTMDB.runtime,
        genres = movieTMDB.genres?.joinToString { it.name.orEmpty() } ?: "",
        overview = movieTMDB.overview,
        video = movieTMDB.videos?.results?.map { mapTrailerDTOToLocalTrailerData(it) }
            ?: emptyList(),
        actor = movieTMDB.credits?.cast?.map {
            mapCastDtoToLocalActorData(
                actor = it,
                movieId = movieTMDB.id?.toLong() ?: 0
            )
        } ?: emptyList(),
        category = "search",
        imdbId = movieTMDB.imdb_id,
        adult = movieTMDB.adult,
        backdropPath = movieTMDB.backdrop_path,
        originalLanguage = movieTMDB.original_language,
        voteCount = movieTMDB.vote_count
    )
}

fun mapMovieDataTMDBToLocalMovieData(movieTMDB: MovieDataTMDB, category: String): LocalMovieData {
    return LocalMovieData(
        movieId = movieTMDB.id,
        movieTitle = movieTMDB.title,
        movieFavorite = false,
        moviePoster = movieTMDB.poster_path,
        movieReleaseDate = movieTMDB.release_date,
        movieRating = movieTMDB.vote_average,
        runtime = movieTMDB.runtime,
        genres = movieTMDB.genres?.joinToString { it.name.orEmpty() } ?: "",
        overview = movieTMDB.overview,
        video = movieTMDB.videos?.results?.map { mapTrailerDTOToLocalTrailerData(it) }
            ?: emptyList(),
        actor = movieTMDB.credits?.cast?.map {
            mapCastDtoToLocalActorData(
                actor = it,
                movieId = movieTMDB.id?.toLong() ?: 0
            )
        } ?: emptyList(),
        category = category,
        imdbId = movieTMDB.imdb_id,
        adult = movieTMDB.adult,
        backdropPath = movieTMDB.backdrop_path,
        originalLanguage = movieTMDB.original_language,
        voteCount = movieTMDB.vote_count
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

fun mapLocalMovieTrailerToTrailerEntity(
    localTrailerData: LocalTrailerData,
    movieId: String
): TrailerEntity {
    return TrailerEntity(
        id = movieId,
        name = localTrailerData.name.orEmpty(),
        key = localTrailerData.key,
        type = localTrailerData.type
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
            runtime = it.runtime,
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

fun convertMovieRoomToMovieDto(localMovieData: LocalMovieData): MovieDataTMDB {
    return MovieDataTMDB(
        adult = localMovieData.adult,
        backdrop_path = localMovieData.backdropPath,
        poster_path = localMovieData.moviePoster,
        budget = null,
        id = localMovieData.movieId,
        imdb_id = null,
        genres = localMovieData.genres.split(", ").map { GenresDTO(0, it) },
        original_language = localMovieData.originalLanguage,
        overview = localMovieData.overview,
        title = localMovieData.movieTitle,
        vote_count = localMovieData.voteCount,
        vote_average = localMovieData.movieRating,
        release_date = localMovieData.movieReleaseDate,
        production_countries = null,
        runtime = localMovieData.runtime,
        credits = CreditsDTO(emptyList()), videos = null
    )
}
