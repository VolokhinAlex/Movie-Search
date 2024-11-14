package com.volokhinaleksey.movie_club.model.remote

import android.os.Parcelable
import com.volokhinaleksey.movie_club.model.ui.Genre
import kotlinx.parcelize.Parcelize

data class GenresDTO(
    val genres: List<GenreDTO>
)

/**
 * The class GenresDTO needs to get Subcategory "genres" from MovieDataTMDB
 * @param id -      ID of the movie genre
 * @param name -    name of the movie genre
 */

@Parcelize
data class GenreDTO(
    val id: Int?,
    val name: String?
) : Parcelable

fun GenreDTO.toGenreUI(): Genre {
    return Genre(
        id = id ?: 0,
        name = name.orEmpty()
    )
}