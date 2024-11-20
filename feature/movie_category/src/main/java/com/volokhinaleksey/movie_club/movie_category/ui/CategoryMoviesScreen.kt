package com.volokhinaleksey.movie_club.movie_category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.movie_category.viewmodel.CategoryMoviesViewModel
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryMoviesScreen(
    category: String,
    onItemClick: (Movie) -> Unit,
    onBack: () -> Unit,
    categoryMoviesViewModel: CategoryMoviesViewModel = koinViewModel(),
) {
    val categoryMovieItems =
        categoryMoviesViewModel.getCategoryMovies(category).collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MovieClubTheme.colors.primaryContainerColor)
    ) {
        Header(category = category, onBack = onBack)
        CategoryMovieList(items = { categoryMovieItems }, onItemClick = onItemClick)
    }
}