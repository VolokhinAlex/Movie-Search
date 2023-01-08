package com.example.java.android1.movie_search.view

sealed class LanguageQuery(val languageQuery: String) {
    object EN : LanguageQuery("en-EN")
}
