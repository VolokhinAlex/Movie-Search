package com.example.java.android1.movie_search.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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