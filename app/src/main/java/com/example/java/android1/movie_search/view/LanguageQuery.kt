package com.example.java.android1.movie_search.view

/**
 * A class for storing the query language. The response language that will be returned from the request
 * @param languageQuery - Response language
 */

sealed class LanguageQuery(val languageQuery: String) {
    object EN : LanguageQuery("en-EN")
}
