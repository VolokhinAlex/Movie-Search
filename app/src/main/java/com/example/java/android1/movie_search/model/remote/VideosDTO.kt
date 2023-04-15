package com.example.java.android1.movie_search.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The class is needed to get a list of trailers
 * @param results - List of movie trailers
 */

@Parcelize
data class VideosDTO(
    val results: List<TrailerDTO>
) : Parcelable