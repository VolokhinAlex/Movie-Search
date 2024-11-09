package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The class for getting and saving data from TMDB in the Model.
 * [ This class created to work with the TMDB API ]
 *
 * @param adult -               Adult movie
 * @param backdrop_path -       Image of movie
 * @param poster_path -         Image of movie
 * @param budget -              Movie budget
 * @param id -                  ID of the movie in the database
 * @param imdb_id -             ID of the movie in the imdb database
 * @param genres -              Genres of movie
 * @param original_language -   The original language of the movie
 * @param overview -            Short description of the movie
 * @param title -               Title of movie
 * @param vote_count -          Number of users who voted
 * @param vote_average -        Popularity ball (0-10) according to IMDB
 * @param release_date -        Premiere date of the movie
 * @param production_countries -The country where the movie was shot
 * @param runtime -             Duration of the movie by time
 * @param credits -             The main characters of the movie
 * @param videos -              Movie trailers
 *
 */

@Parcelize
data class MovieDataTMDB(
    val adult: Boolean?,
    val backdrop_path: String?,
    val poster_path: String?,
    val budget: Int?,
    val id: Int?,
    val imdb_id: String?,
    val genres: List<GenresDTO>?,
    val original_language: String?,
    val overview: String?,
    val title: String?,
    val vote_count: Int?,
    val vote_average: Double?,
    val release_date: String?,
    val production_countries: List<CountriesDTO>?,
    val runtime: Int?,
    val credits: CreditsDTO?,
    val videos: VideosDTO?
) : Parcelable