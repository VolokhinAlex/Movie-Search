package com.example.java.android1.movie_search.model

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

/**
 * A class for easily getting a category movies
 * @param results - List of movies
 */

@Parcelize
data class CategoryMoviesTMDB(
    val results: List<MovieDataTMDB>
) : Parcelable

/**
 * The class CountriesDTO needs to get Subcategory "production_countries" from MovieDataTMDB
 * @param iso_3166_1 -  Code for the representation of names of countries
 * @param name -        The name of the country
 */

@Parcelize
data class CountriesDTO(
    val iso_3166_1: String?,
    val name: String?
) : Parcelable

/**
 * The class GenresDTO needs to get Subcategory "genres" from MovieDataTMDB
 * @param id -      ID of the movie genre
 * @param name -    name of the movie genre
 */

@Parcelize
data class GenresDTO(
    val id: Int?,
    val name: String?
) : Parcelable

/**
 * The classes CreditsDTO and CastDTO need to get Subcategory "credits" and "cast" from MovieDataTMDB
 * @param cast - List of movie actors
 */

@Parcelize
data class CreditsDTO(val cast: List<CastDTO>) : Parcelable

/**
 * The CastDTO class contains data about the character
 * @param id -              Actor id
 * @param name -            Full name of the actor
 * @param profile_path -    Photo of the actor
 * @param character -       Character Name
 */

@Parcelize
data class CastDTO(
    val id: Long?,
    val name: String?,
    val profile_path: String?,
    val character: String?
) : Parcelable

/**
 * The class is needed to get a list of trailers
 * @param results - List of movie trailers
 */

@Parcelize
data class VideosDTO(
    val results: List<TrailerDTO>
) : Parcelable

/**
 * The Trailer class contains data about the trailer of movie
 * @param name - The name of the movie
 * @param key -  The key to finding the trailer on the YouTube site
 * @param type - Video Type
 * @param id -   Trailer id
 */

@Parcelize
data class TrailerDTO(
    val name: String?,
    val key: String?,
    val type: String?,
    val id: String?
) : Parcelable

/**
 * The CastDTO class contains data about the Actor
 * @param biography -       Biography of the actor
 * @param birthday -        Birthday of the actor
 * @param id -              Actor id
 * @param imdb_id -         Id to search for an actor on the imdb site
 * @param name -            Full name of the actor
 * @param place_of_birth -  The actor's place of birth
 * @param popularity -      The popularity of the actor
 * @param profile_path -    Photo of the actor
 */

@Parcelize
data class ActorDTO(
    val biography: String?,
    val birthday: String?,
    val id: Long?,
    val imdb_id: String?,
    val name: String?,
    val place_of_birth: String?,
    val popularity: Double?,
    val profile_path: String?
) : Parcelable