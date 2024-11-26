package com.volokhinaleksey.movie_club.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The class for getting and saving data from TMDB in the Model.
 * [ This class created to work with the TMDB API ]
 *
 * @param adult Adult movie
 * @param backdropPath Image of movie
 * @param posterPath Image of movie
 * @param budget Movie budget
 * @param id ID of the movie in the database
 * @param imdbId ID of the movie in the imdb database
 * @param genres Genres of movie
 * @param originalLanguage The original language of the movie
 * @param overview Short description of the movie
 * @param title Title of movie
 * @param voteCount Number of users who voted
 * @param voteAverage Popularity ball (0-10) according to IMDB
 * @param releaseDate Premiere date of the movie
 * @param runtime Duration of the movie by time
 * @param credits The main characters of the movie
 * @param videos Movie trailers
 *
 */

@Serializable
data class MovieDataTMDB(
    @SerialName("adult") val adult: Boolean? = false,
    @SerialName("backdrop_path") val backdropPath: String? = "",
    @SerialName("poster_path") val posterPath: String? = "",
    @SerialName("budget") val budget: Int? = 0,
    @SerialName("id") val id: Int?,
    @SerialName("imdb_id") val imdbId: String? = "",
    @SerialName("genre_ids") val genres: List<Int>? = null,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("title") val title: String? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("runtime") val runtime: Int? = 0,
    @SerialName("credits") val credits: ActorsDTO? = null,
    @SerialName("videos") val videos: VideosDTO? = null
)