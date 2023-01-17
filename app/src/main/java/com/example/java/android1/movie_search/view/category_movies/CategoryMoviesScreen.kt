package com.example.java.android1.movie_search.view.category_movies

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.view.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.navigation.navigate
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ListMoviesPagination
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModel

const val ARG_CATEGORY_NAME_DATA = "CategoryMoviesData"

/**
 * The main method for the layout of the entire screen
 * @param categoryName - The category movies in the screen
 * @param navController - Controller for screen navigation
 * @param categoryMoviesViewModel - Category Movies View Model [CategoryMoviesViewModel]
 */

@Composable
fun CategoryMoviesScreen(
    categoryName: String,
    navController: NavController,
    categoryMoviesViewModel: CategoryMoviesViewModel
) {
    val categoryMoviesData =
        categoryMoviesViewModel.getCategoryMoviesFromRemoteServer(categoryName)
            .collectAsLazyPagingItems()
    Column(modifier = Modifier.background(PrimaryColor80)) {
        when (categoryName) {
            MovieCategory.NowPlaying.queryName -> HeaderCategoryMoviesScreen(
                MovieCategory.NowPlaying.title,
                navController
            )
            MovieCategory.TopRated.queryName -> HeaderCategoryMoviesScreen(
                MovieCategory.TopRated.title,
                navController
            )
            MovieCategory.Upcoming.queryName -> HeaderCategoryMoviesScreen(
                MovieCategory.Upcoming.title,
                navController
            )
            MovieCategory.Popular.queryName -> HeaderCategoryMoviesScreen(
                MovieCategory.Popular.title,
                navController
            )
        }
        ListMoviesPagination(categoryMoviesData) { movieData ->
            val detailsMovieBundle = Bundle()
            detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, movieData)
            navController.navigate(ScreenState.DetailsScreen.route, detailsMovieBundle)
        }
    }
}

/**
 * The method sets the title of the screen and adds a button to go to the previous screen (Toolbar)
 * @param title - The title of the category movies
 * @param navController - Needed to navigate back
 */

@Composable
private fun HeaderCategoryMoviesScreen(title: Int, navController: NavController) {
    Row(
        modifier = Modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(end = 5.dp),
            onClick = {
                navController.navigateUp()
            }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = stringResource(id = title),
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
