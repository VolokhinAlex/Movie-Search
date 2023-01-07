package com.example.java.android1.movie_search.view.search

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.example.java.android1.movie_search.model.MovieDataTMDB

/**
 * The class needed to remember search states
 */

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<MovieDataTMDB>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)

}