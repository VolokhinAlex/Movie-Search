package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class MovieDataTMDB(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String?,
    val budget: Int?,
    val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("genre_ids") val genres: List<Int>?,
    @SerializedName("original_language") val originalLanguage: String?,
    val overview: String?,
    val title: String?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val credits: ActorsDTO?,
    val videos: VideosDTO?
) : Parcelable