package com.example.java.android1.movie_search.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrailerUI(
    val id: String = "",
    val name: String = "",
    val key: String = "",
    val type: String = ""
): Parcelable