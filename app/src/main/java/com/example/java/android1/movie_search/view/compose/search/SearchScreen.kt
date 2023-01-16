package com.example.java.android1.movie_search.view.compose.search

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.navigation.navigate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor70
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    navController: NavController,
    searchState: SearchState = rememberSearchState(),
    searchViewModel: SearchViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80)
    ) {
        Column(modifier = Modifier.padding(top = 15.dp)) {
            SearchField(modifier = Modifier, searchViewModel, searchState, navController)
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    state: SearchState,
    navController: NavController
) {
    SearchBar(
        query = state.query,
        onQueryChange = { state.query = it },
        onSearchFocusChange = { state.focused = it },
        onClearQuery = { state.query = TextFieldValue("") },
        onBack = { state.query = TextFieldValue("") },
        searching = state.searching,
        focused = state.focused,
        modifier = modifier,
        searchHint = "Search a movie"
    )

    LaunchedEffect(state.query.text) {
        state.searching = true
        delay(100)
        state.searching = false
    }
    if (state.query.text != "") {
        val moviesListBySearch =
            viewModel.getMoviesBySearchFromRemoteServer(state.query.text).collectAsLazyPagingItems()
        ShowSearchMovies(moviesListBySearch, navController)
    }
}

@Composable
private fun ShowSearchMovies(
    lazyMovieItems: LazyPagingItems<MovieDataTMDB>,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(lazyMovieItems.itemCount) { item ->
            lazyMovieItems[item]?.let { movieData ->
                MovieCard(
                    modifier = Modifier
                        .size(width = 160.dp, height = 350.dp)
                        .padding(bottom = 10.dp)
                        .clickable {
                            val bundle = Bundle()
                            bundle.putParcelable(MOVIE_DATA_KEY, movieData)
                            navController.navigate(ScreenState.DetailsScreen.route, bundle)
                        },
                    movieDataTMDB = movieData
                )
            }
        }
        item(span = { GridItemSpan(2) }) {
            lazyMovieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        Loader()
                    }
                    loadState.append is LoadState.Loading -> {
                        Loader()
                    }
                    loadState.refresh is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.refresh as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) {
                            lazyMovieItems.retry()
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.append as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) {
                            lazyMovieItems.retry()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<MovieDataTMDB> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            searchResults = searchResults
        )
    }
}

@Composable
private fun SearchHint(modifier: Modifier = Modifier, searchHint: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            color = Color(0xFFFFFFFF),
            text = searchHint,
        )
    }
}

@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier,
    searchHint: String
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = if (!focused) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = PrimaryColor70,
        shape = RoundedCornerShape(percent = 50),
    ) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
        ) {

            if (query.text.isEmpty()) {
                SearchHint(modifier.padding(start = 24.dp, end = 8.dp), searchHint)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                        .focusRequester(focusRequester)
                        .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    singleLine = true
                )

            }
        }

    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier,
    searchHint: String
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            IconButton(
                modifier = Modifier.padding(start = 2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        SearchTextField(
            query,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            focused,
            modifier.weight(1f),
            searchHint
        )
    }
}