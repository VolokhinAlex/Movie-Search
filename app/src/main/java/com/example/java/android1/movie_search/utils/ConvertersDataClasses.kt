package com.example.java.android1.movie_search.utils

import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.model.local.ActorEntity
import com.example.java.android1.movie_search.model.local.MovieEntity
import com.example.java.android1.movie_search.model.local.TrailerEntity
import com.example.java.android1.movie_search.model.old.remote.ActorDTO
import com.example.java.android1.movie_search.model.old.remote.CastDTO
import com.example.java.android1.movie_search.model.old.remote.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.old.remote.CreditsDTO
import com.example.java.android1.movie_search.model.old.remote.GenresDTO
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.model.old.remote.TrailerDTO
import com.example.java.android1.movie_search.model.old.remote.VideosDTO
import com.example.java.android1.movie_search.model.ui.ActorUI
import com.example.java.android1.movie_search.model.ui.CategoryMovieUI
import com.example.java.android1.movie_search.model.ui.GenreUI
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.model.ui.TrailerUI

/**
 * Auxiliary methods for convenient conversion of one data type to another
 */


fun mapMovieCategoryMoviesTMDBToCategoryMovieUI(
    category: String,
    categoryMoviesTMDB: CategoryMoviesTMDB
): CategoryMovieUI {
    return CategoryMovieUI(
        category = category,
        movie = categoryMoviesTMDB.results.map {
            mapMovieDataTMDBToMovieUI(
                moviesTMDB = it,
                category = category
            )
        })
}

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
        id = actorDTO.id ?: 0,
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
        video = trailers.map { mapTrailerEntityToTrailerUI(it) },
        actor = actors.map { mapActorEntityToActorUI(it) },
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
        id = actorEntity.id ?: 0,
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
        actors = localMovieData.actor,
        videos = localMovieData.video,
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
        video = movieTMDB.videos?.results?.map { mapTrailerDtoToTrailerUI(it) } ?: emptyList(),
        actor = movieTMDB.credits?.cast?.map { mapCastDtoToActorUI(it) } ?: emptyList(),
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
        video = movieTMDB.videos?.results?.map { mapTrailerDtoToTrailerUI(it) } ?: emptyList(),
        actor = movieTMDB.credits?.cast?.map { mapCastDtoToActorUI(it) } ?: emptyList(),
        category = category,
        imdbId = movieTMDB.imdb_id,
        adult = movieTMDB.adult,
        backdropPath = movieTMDB.backdrop_path,
        originalLanguage = movieTMDB.original_language,
        voteCount = movieTMDB.vote_count
    )
}

//fun convertMovieEntityToMovieDataRoom(
//    movieEntity: MovieEntity?,
//    trailerEntity: List<TrailerEntity>,
//    actorEntity: List<ActorEntity>
//): MovieDataRoom =
//    MovieDataRoom(
//        movieId = movieEntity?.movieId,
//        movieTitle = movieEntity?.movieTitle,
//        movieNote = movieEntity?.movieNote,
//        movieFavorite = movieEntity?.movieFavorite,
//        moviePoster = movieEntity?.moviePoster,
//        movieReleaseDate = movieEntity?.movieReleaseDate,
//        movieRating = movieEntity?.movieRating,
//        runtime = movieEntity?.runtime,
//        genres = movieEntity?.genres.orEmpty(),
//        overview = movieEntity?.overview,
//        video = trailerEntity.map {
//            TrailerUI(
//                id = it.id,
//                name = it.name,
//                key = it.key.orEmpty(),
//                type = it.type.orEmpty()
//            )
//        },
//        actor = actorEntity.map {
//            ActorUI(
//                actorId = it.actorId,
//                id = it.id ?: 0,
//                biography = it.biography.orEmpty(),
//                birthday = it.birthday.orEmpty(),
//                imdbId = it.imdbId.orEmpty(),
//                name = it.name.orEmpty(),
//                placeOfBirth = it.placeOfBirth.orEmpty(),
//                popularity = it.popularity ?: 0.0,
//                profilePath = it.profilePath.orEmpty(),
//                character = it.character.orEmpty()
//            )
//        },
//        category = movieEntity?.category.orEmpty(),
//        imdbId = movieEntity?.imdbId,
//        adult = movieEntity?.adult,
//        backdropPath = movieEntity?.backdropPath,
//        originalLanguage = movieEntity?.originalLanguage,
//        voteCount = movieEntity?.voteCount
//    )

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

fun convertMovieDtoToMovieEntity(moviesTMDB: MovieDataTMDB, category: String): MovieEntity =
    MovieEntity(
        movieId = moviesTMDB.id,
        movieTitle = moviesTMDB.title,
        movieFavorite = false,
        moviePoster = moviesTMDB.poster_path,
        movieReleaseDate = moviesTMDB.release_date,
        movieRating = moviesTMDB.vote_average,
        runtime = moviesTMDB.runtime,
        genres = moviesTMDB.genres?.joinToString { it.name.orEmpty() } ?: "",
        overview = moviesTMDB.overview,
        category = category,
        adult = moviesTMDB.adult,
        imdbId = moviesTMDB.imdb_id,
        backdropPath = moviesTMDB.backdrop_path,
        originalLanguage = moviesTMDB.original_language,
        voteCount = moviesTMDB.vote_count
    )

fun convertMovieEntityToMovieDto(
    movie: LocalMovieData,
    actors: List<ActorEntity>,
    trailer: List<TrailerEntity>
): MovieDataTMDB =
    MovieDataTMDB(
        adult = movie.adult,
        backdrop_path = movie.backdropPath,
        poster_path = movie.moviePoster,
        budget = null,
        id = movie.movieId,
        imdb_id = movie.imdbId,
        genres = movie.genres.split(",").map { GenresDTO(id = 0, name = it) },
        original_language = movie.originalLanguage,
        overview = movie.overview,
        title = movie.movieTitle,
        vote_count = movie.voteCount,
        vote_average = movie.movieRating,
        release_date = movie.movieReleaseDate,
        production_countries = null,
        runtime = movie.runtime,
        credits = CreditsDTO(cast = actors.map {
            CastDTO(
                id = it.id,
                name = it.name,
                profile_path = it.profilePath,
                character = it.character
            )
        }),
        videos = VideosDTO(results = trailer.map {
            TrailerDTO(
                id = it.id,
                name = it.name,
                key = it.key,
                type = it.type
            )
        })
    )

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
