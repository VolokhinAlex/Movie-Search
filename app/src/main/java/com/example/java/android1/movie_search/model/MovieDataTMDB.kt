package com.example.java.android1.movie_search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The class for getting and saving data from TMDB in the Model.
 * [ This class created to work with the TMDB API ]
 *
 * @param id -                  ID of the movie in the database
 * @param imdb_id -             ID of the movie in the imdb database
 * @param title -               Title of movie
 * @param overview -            Short description of the movie
 * @param backdrop_path -       Image of movie
 * @param credits -             The main characters of the movie
 * @param genres -              Genres of movie
 * @param production_countries -The country where the movie was shot
 * @param release_date -        Premiere date of the movie
 * @param vote_average -        Popularity ball (0-10) according to IMDB
 * @param vote_count -          Number of users who voted
 * @param adult -               Adult movie
 * @param original_language -   The original language of the movie
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
    val credits: CreditsDTO,
    val videos: Videos?
) : Parcelable

@Parcelize
data class CategoryMoviesTMDB(
    val results: List<MovieDataTMDB>
) : Parcelable

/**
 * The class CountriesDTO needs to get Subcategory "production_countries" from MovieDataTMDB
 * @param iso_3166_1 - Code for the representation of names of countries
 * @param name - The name of the country
 */

@Parcelize
data class CountriesDTO(
    val iso_3166_1: String?,
    val name: String?
) : Parcelable

/**
 * The class GenresDTO needs to get Subcategory "genres" from MovieDataTMDB
 * @param id - ID of the movie genre
 * @param name - name of the movie genre
 */

@Parcelize
data class GenresDTO(
    val id: Int?,
    val name: String?
) : Parcelable

/**
 * The classes CreditsDTO and CastDTO need to get Subcategory "credits" and "cast" from MovieDataTMDB
 */

@Parcelize
data class CreditsDTO(val cast: List<CastDTO>) : Parcelable

@Parcelize
data class CastDTO(
    val name: String?,
    val profile_path: String?,
    val character: String?
) : Parcelable

@Parcelize
data class Videos(
    val results: Trailer
) : Parcelable

@Parcelize
data class Trailer(
    val name: String?,
    val key: String?,
    val type: String?,
    val id: String?
) : Parcelable