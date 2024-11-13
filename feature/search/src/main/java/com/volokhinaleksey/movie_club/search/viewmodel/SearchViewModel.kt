package com.volokhinaleksey.movie_club.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.volokhinaleksey.movie_club.domain.SearchInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isFocused = MutableStateFlow(false)
    val isFocused: StateFlow<Boolean> = _isFocused

    val movieItems = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { searchInteractor.getMoviesBySearch(query = it, true) }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSearchFocus(isFocused: Boolean) {
        _isFocused.value = isFocused
    }
}

