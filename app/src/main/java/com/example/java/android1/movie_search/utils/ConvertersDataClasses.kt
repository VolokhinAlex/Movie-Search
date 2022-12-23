package com.example.java.android1.movie_search.utils

import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.room.MovieEntity

fun converterMovieEntityToMovieDataRoom(entity: MovieEntity): MovieDataRoom {
    return MovieDataRoom(entity.movieId, entity.movieTitle, entity.movieNote, entity.movieFavorite)
}

fun converterMovieDtoToMovieEntity(moviesTMDB: MovieDataTMDB): MovieEntity =
    MovieEntity(moviesTMDB.id, moviesTMDB.title, "", false)