package com.example.java.android1.movie_search.app

import android.location.Location

sealed class MapState {
    data class Success(val data: Location) : MapState()
    data class UnableGps(val message: String) : MapState()
    data class Error(val error: Throwable) : MapState()
}