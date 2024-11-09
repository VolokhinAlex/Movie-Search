package com.volokhinaleksey.movie_club.view.search

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.volokhinaleksey.movie_club.R
import com.volokhinaleksey.movie_club.view.MOVIE_DATA_KEY
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor70
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.view.theme.SearchFieldColor
import com.volokhinaleksey.movie_club.view.widgets.ListMoviesPagination
import com.volokhinaleksey.movie_club.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

/**
 * The main method for the layout of the search screen methods
 * @param navController - Controller for screen navigation
 * @param searchState - Saved search states
 * @param searchViewModel - Search View Model [SearchViewModel]
 */

@Composable
fun SearchScreen(
    navController: NavController,
    searchState: SearchState = rememberSearchState(),
    searchViewModel: SearchViewModel = koinViewModel(),
    networkStatus: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80)
    ) {
        Column(modifier = Modifier.padding(top = 15.dp)) {
            SearchField(
                modifier = Modifier,
                searchViewModel = searchViewModel,
                searchState = searchState,
                navController = navController,
                networkStatus = networkStatus
            )
        }
    }
}

/**
 * The method for displaying, and implementing a search
 *  @param searchState - Saved search states
 *  @param searchViewModel - Search View Model [SearchViewModel]
 *  @param navController - Controller for screen navigation
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    searchState: SearchState,
    navController: NavController,
    networkStatus: Boolean
) {
    SearchBar(
        query = searchState.query,
        onQueryChange = { searchState.query = it },
        onSearchFocusChange = { searchState.focused = it },
        onClearQuery = { searchState.query = TextFieldValue("") },
        onBack = { searchState.query = TextFieldValue("") },
        searching = searchState.searching,
        focused = searchState.focused,
        modifier = modifier,
        searchHint = stringResource(id = R.string.search_hint)
    )
    LaunchedEffect(searchState.query.text) {
        searchState.searching = true
        delay(100)
        searchState.searching = false
    }
    if (searchState.query.text != "") {
        val listMoviesBySearch =
            searchViewModel.getMoviesBySearch(
                query = searchState.query.text,
                isOnline = networkStatus
            )
                .collectAsLazyPagingItems()
        ListMoviesPagination(listMoviesBySearch) { movieData ->
            val detailsMovieBundle = Bundle()
            detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, movieData)
            navController.navigate(ScreenState.DetailsScreen.route, detailsMovieBundle)
        }
    }
}

/**
 * The method for remembering all search states
 * @param query - The entered value in the text field
 * @param focused - Equals true if the user opened by a text field otherwise false
 * @param searching - Equals true while the user enters some text in the text field otherwise false
 */

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false
): SearchState = remember {
    SearchState(
        query = query,
        focused = focused,
        searching = searching
    )
}

/**
 * The method for showing hint in the search text field
 * @param searchHint - Hint of the text input field
 */

@Composable
private fun SearchTextFieldHint(modifier: Modifier = Modifier, searchHint: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Text(
            color = SearchFieldColor,
            text = searchHint,
        )
    }
}

/**
 * The method for creating a search text field with states
 * @param query - The value written in the text field
 * @param onQueryChange - Occurs every time the value in the text field changes
 * @param onSearchFocusChange - Occurs when the text field is clicked and when the back button is clicked
 * @param onClearQuery - Occurs to clear a value in a text field
 * @param searching - Search states. Occurs when the user types some text
 * @param focused - Occurs if there is a focus on the text field
 * @param searchHint - Hint for a text field
 */

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
                SearchTextFieldHint(modifier.padding(start = 24.dp, end = 8.dp), searchHint)
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

/**
 * The method for displaying the search text field
 * @param query - The value written in the text field
 * @param onQueryChange - Occurs every time the value in the text field changes
 * @param onSearchFocusChange - Occurs when the text field is clicked and when the back button is clicked
 * @param onClearQuery - Occurs to clear a value in a text field
 * @param onBack - Occurs when you click on the back arrow
 * @param searching - Search states. Occurs when the user types some text
 * @param focused - Occurs if there is a focus on the text field
 * @param searchHint - Hint for a text field
 */

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
