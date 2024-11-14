package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): Movie

    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>>

}

/**
 * Minutes are converted to the format of hours and minutes
 * @sample min = 96 -> 1h 36min
 */

internal fun timeToFormatHoursAndMinutes(min: Int): String {
    val hour = min / 60
    val minutes = min % 60
    return String.format("%02dh %02dmin", hour, minutes)
}