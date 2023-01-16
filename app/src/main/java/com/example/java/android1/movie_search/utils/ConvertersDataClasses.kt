package com.example.java.android1.movie_search.utils

import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieEntity

/**
 * Auxiliary methods for convenient conversion of one data type to another
 */

fun convertMovieEntityToMovieDataRoom(entity: MovieEntity?): MovieDataRoom =
    MovieDataRoom(
        entity?.movieId,
        entity?.movieTitle,
        entity?.movieNote,
        entity?.movieFavorite,
        entity?.moviePoster,
        entity?.movieReleaseDate,
        entity?.movieRating
    )

fun convertMovieEntityToListMovieDataRoom(entity: List<MovieEntity>): List<MovieDataRoom> =
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

fun convertMovieDtoToMovieEntity(moviesTMDB: MovieDataTMDB): MovieEntity =
    MovieEntity(
        moviesTMDB.id,
        moviesTMDB.title,
        "",
        false,
        moviesTMDB.poster_path,
        moviesTMDB.release_date,
        moviesTMDB.vote_average
    )

fun convertMovieRoomToMovieDto(movieDataRoom: MovieDataRoom): MovieDataTMDB {
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
