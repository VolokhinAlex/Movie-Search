package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A class for easily getting a category movies
 * @param results - List of movies
 */

@Parcelize
data class CategoryMoviesTMDB(
    val results: List<MovieDataTMDB>
) : Parcelable