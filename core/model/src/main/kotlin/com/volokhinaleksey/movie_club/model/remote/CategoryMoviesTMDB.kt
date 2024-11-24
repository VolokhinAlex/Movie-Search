package com.volokhinaleksey.movie_club.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A class for easily getting a category movies
 * @param results - List of movies
 */

@Serializable
data class CategoryMoviesTMDB(
    @SerialName("results") val results: List<MovieDataTMDB>
)