package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.volokhinaleksey.movie_club.model.ui.GenreUI
import kotlinx.parcelize.Parcelize

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

fun GenresDTO.toGenreUI(): GenreUI {
    return GenreUI(
        id = id ?: 0,
        name = name.orEmpty()
    )
}