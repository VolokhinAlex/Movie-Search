package com.example.java.android1.movie_search.model

/**
 * The class for getting and saving data from TMDB in the Model.
 * [ This class created to work with the TMDB API ]
 */

data class MovieDataTMDB(
    val adult: Boolean?,
    val backdrop_path: String?,
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
    val credits: CreditsDTO
)

data class CategoryMoviesTMDB(
    val results: List<MovieDataTMDB>
)

/**
 * The class CountriesDTO needs to get Subcategory "production_countries" from MovieDataTMDB
 * @param iso_3166_1 - Code for the representation of names of countries
 * @param name - The name of the country
 */

data class CountriesDTO(
    val iso_3166_1: String?,
    val name: String?
)

/**
 * The class GenresDTO needs to get Subcategory "genres" from MovieDataTMDB
 * @param id - ID of the movie genre
 * @param name - name of the movie genre
 */

data class GenresDTO(
    val id: Int?,
    val name: String?
)

/**
 * The classes CreditsDTO and CastDTO need to get Subcategory "credits" and "cast" from MovieDataTMDB
 */

data class CreditsDTO(val cast: List<CastDTO>)

data class CastDTO(
    val name: String?,
    val profile_path: String?,
    val character: String?
)