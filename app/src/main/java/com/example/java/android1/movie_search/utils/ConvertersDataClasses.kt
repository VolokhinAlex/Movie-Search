package com.example.java.android1.movie_search.utils

import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieEntity

fun converterMovieEntityToMovieDataRoom(entity: MovieEntity): MovieDataRoom =
    MovieDataRoom(
        entity.movieId,
        entity.movieTitle,
        entity.movieNote,
        entity.movieFavorite,
        entity.moviePoster,
        entity.movieReleaseDate,
        entity.movieRating
    )

fun converterMovieEntityToListMovieDataRoom(entity: List<MovieEntity>): List<MovieDataRoom> =
    entity.map {
        MovieDataRoom(
            it.movieId,
            it.movieTitle,
            it.movieNote,
            it.movieFavorite,
            it.moviePoster,
            it.movieReleaseDate,
            it.movieRating
        )
    }

fun converterMovieDtoToMovieEntity(moviesTMDB: MovieDataTMDB): MovieEntity =
    MovieEntity(
        moviesTMDB.id,
        moviesTMDB.title,
        "",
        false,
        moviesTMDB.poster_path,
        moviesTMDB.release_date,
        moviesTMDB.vote_average
    )

fun converterMovieRoomToMovieDto(movieDataRoom: MovieDataRoom): MovieDataTMDB {
    return MovieDataTMDB(
        null,
        null,
        movieDataRoom.moviePoster,
        null,
        movieDataRoom.movieId,
        null,
        null,
        null,
        null,
        movieDataRoom.movieTitle,
        null,
        movieDataRoom.movieRating,
        movieDataRoom.movieReleaseDate,
        null,
        null,
        CreditsDTO(emptyList()), null
    )
}
