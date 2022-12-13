package com.example.java.android1.movie_search.model

/**
 * Data class for keep child data of recyclerview on the home page
 * @param title - name of the list category
 * @param listData - data of the list category for the child recyclerview
 */

data class MovieChildListData(
    val title: String,
    val listData: List<MovieData>
)