package com.example.java.android1.movie_search.model


/**
 * The class is responsible for storing information about the movie.
 * @param id -               ID of the movie in the database
 * @param imdbId -           ID of the movie in the imdb database
 * @param title -            Title of movie
 * @param overview -         Short description of the movie
 * @param backdropPath -     Image of movie
 * @param actors -           The main characters of the movie
 * @param genres -           Genres of movie
 * @param country -          The country where the movie was shot
 * @param releaseDate -      Premiere date of the movie
 * @param rationOfMovie -    Popularity ball (0-10) according to IMDB
 * @param voteCount -        Number of users who voted
 * @param age -              Acceptable age for watch the movie
 * @param adult -            Adult movie
 * @param originalLanguage - The original language of the movie
 */

data class MovieData(
    val id: Int,
    val imdbId: String,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val actors: List<String>,
    val genres: List<String>,
    val country: String,
    val releaseDate: String,
    val rationOfMovie: Double,
    val voteCount: Int,
    val age: Int,
    val adult: Boolean = false,
    val originalLanguage: String,
)