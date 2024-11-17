package com.volokhinaleksey.movie_club.movie_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.volokhinaleksey.movie_club.domain.CategoryInteractor
import com.volokhinaleksey.movie_club.domain.LocaleInteractor

class CategoryMoviesViewModel(
    private val categoryInteractor: CategoryInteractor,
    private val localeInteractor: LocaleInteractor,
) : ViewModel() {

    fun getCategoryMovies(categoryId: String) = categoryInteractor
        .getCategoryMovies(categoryId, localeInteractor.getCurrentLanguage())
        .cachedIn(viewModelScope)
}