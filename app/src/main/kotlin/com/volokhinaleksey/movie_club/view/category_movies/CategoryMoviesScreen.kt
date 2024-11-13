package com.volokhinaleksey.movie_club.view.category_movies

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
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.uikit.theme.TITLE_SIZE
import com.volokhinaleksey.movie_club.uikit.widgets.MovieListPaging
import com.volokhinaleksey.movie_club.view.MOVIE_DATA_KEY
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.viewmodel.CategoryMoviesViewModel
import org.koin.androidx.compose.koinViewModel

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
    categoryMoviesViewModel: CategoryMoviesViewModel = koinViewModel(),
    networkStatus: Boolean
) {
    val categoryMoviesData =
        categoryMoviesViewModel.getCategoryMoviesFromRemoteServer(
            category = categoryName,
            isOnline = networkStatus
        ).collectAsLazyPagingItems()
    Column(modifier = Modifier.background(PrimaryColor80)) {
//        when (categoryName) {
//            MovieCategory.NowPlaying.id -> HeaderCategoryMoviesScreen(
//                title = MovieCategory.NowPlaying.title,
//                navController = navController
//            )
//            MovieCategory.TopRated.id -> HeaderCategoryMoviesScreen(
//                title = MovieCategory.TopRated.title,
//                navController = navController
//            )
//            MovieCategory.Upcoming.id -> HeaderCategoryMoviesScreen(
//                title = MovieCategory.Upcoming.title,
//                navController = navController
//            )
//            MovieCategory.Popular.id -> HeaderCategoryMoviesScreen(
//                title = MovieCategory.Popular.title,
//                navController = navController
//            )
//        }
        MovieListPaging(categoryMoviesData) { movieData ->
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
