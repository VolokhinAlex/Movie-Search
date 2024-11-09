package com.volokhinaleksey.movie_club.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.movie_club.R
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.utils.convertStringFullDateToOnlyYear
import com.volokhinaleksey.movie_club.utils.timeToFormatHoursAndMinutes
import com.volokhinaleksey.movie_club.view.LanguageQuery
import com.volokhinaleksey.movie_club.view.MOVIE_DATA_KEY
import com.volokhinaleksey.movie_club.view.actor_details.ARG_ACTOR_ID
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.view.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.view.theme.DETAILS_PRIMARY_PAGING
import com.volokhinaleksey.movie_club.view.theme.DETAILS_PRIMARY_SIZE
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.view.theme.TITLE_SIZE
import com.volokhinaleksey.movie_club.view.theme.TransparentColor
import com.volokhinaleksey.movie_club.view.widgets.ErrorMessage
import com.volokhinaleksey.movie_club.view.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.view.widgets.MovieCard
import com.volokhinaleksey.movie_club.viewmodel.DetailsViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

/**
 * The main method [DetailsScreen] that combines all the necessary methods for this screen
 * @param movie - Movie Data has gotten From Remote Server
 * @param navController - Controller for screen navigation
 * @param movieDetailsViewModel - Details View Model [DetailsViewModel]
 */

@Composable
fun DetailsScreen(
    movie: MovieUI,
    navController: NavController,
    networkStatus: Boolean,
    movieDetailsViewModel: DetailsViewModel = koinViewModel(),
) {
    LaunchedEffect(true) {
        movieDetailsViewModel.getMovieDetails(
            movieId = movie.id,
            language = LanguageQuery.EN.languageQuery,
            isOnline = networkStatus
        )
    }
    Column(
        modifier = Modifier
            .background(PrimaryColor80)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        movieDetailsViewModel.detailsMovieData.observeAsState().value?.let { state ->
            RenderMovieDetailsDataFromRemoteServer(
                movieState = state,
                movieDetailsViewModel = movieDetailsViewModel,
                navController = navController,
                movieId = movie.id,
                networkStatus = networkStatus
            )
        }
    }
}

/**
 * The method processes state from the remote server
 * @param movieState - The state that came from the remote server. [MovieState]
 * @param movieDetailsViewModel - ViewModel for logic processing
 * @param navController - To navigate back
 * @param movieId - The movie id
 */

@Composable
private fun RenderMovieDetailsDataFromRemoteServer(
    movieState: MovieState,
    movieDetailsViewModel: DetailsViewModel,
    navController: NavController,
    movieId: Int,
    networkStatus: Boolean
) {
    when (movieState) {
        is MovieState.Error -> movieState.errorMessage.localizedMessage?.let { message ->
            ErrorMessage(message = message) {
                movieDetailsViewModel.getMovieDetails(
                    movieId = movieId,
                    language = LanguageQuery.EN.languageQuery,
                    isOnline = networkStatus
                )
            }
        }

        MovieState.Loading -> LoadingProgressBar()
        is MovieState.Success -> {
            val movieDetailsData = movieState.data[0]
            HeaderDetailsScreen(movieDetailsData, navController)
            Column(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
            ) {
                CenterDetailsScreen(
                    movie = movieDetailsData,
                    movieDetailsViewModel = movieDetailsViewModel
                )
                MovieCasts(movie = movieDetailsData, navController = navController)
                if (movieDetailsData.videos.isNotEmpty()) {
                    MovieTrailer(movieDetailsData = movieDetailsData)
                }
                SimilarMovies(
                    detailsViewModel = movieDetailsViewModel,
                    movieId = movieDetailsData.id,
                    navController = navController,
                    networkStatus = networkStatus
                )
            }
        }
    }
}

/**
 * The method of adding a movie picture in the header of screen
 * @param movie - Movie Data has gotten From Remote Server
 * @param navController - To navigate back
 */

@Composable
private fun HeaderDetailsScreen(movie: MovieUI, navController: NavController) {
    Box {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            loading = { LoadingProgressBar() },
            contentDescription = "movie_poster",
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = {
                navController.navigateUp()
            }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name,
                tint = Color.White
            )
        }
    }
}

/**
 * The method adds detailed information about the movie
 * @param movie - Details of movie that comes from remote server
 * @param movieDetailsViewModel - Details View Model
 */

@Composable
private fun CenterDetailsScreen(
    movie: MovieUI,
    movieDetailsViewModel: DetailsViewModel
) {
    val ratingFormat = DecimalFormat("#.#")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = movie.title,
            color = Color.White,
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
        )
        MovieFavorite(
            movieDetailsViewModel = movieDetailsViewModel,
            movieId = movie.id,
            movieFavorite = movie.favorite
        )
    }
    MovieGenres(movie = movie)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "IMDB ${ratingFormat.format(movie.voteAverage)}",
            modifier = Modifier.padding(end = 5.dp),
            color = Color.White, fontSize = DETAILS_PRIMARY_SIZE
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
            contentDescription = "icon_rating",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(22.dp)
        )
        Text(
            text = convertStringFullDateToOnlyYear(movie.releaseDate),
            modifier = Modifier.padding(end = 15.dp),
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
        Text(
            text = timeToFormatHoursAndMinutes(movie.runtime),
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
    }
    MovieOverview(movie = movie)
}

/**
 * The method adds a list of movie genres
 * @param movie - Details of movie that comes from remote server
 */

@Composable
private fun MovieGenres(movie: MovieUI) {
    Box(
        modifier = Modifier.padding(
            top = DETAILS_PRIMARY_PAGING,
            bottom = DETAILS_PRIMARY_PAGING
        )
    ) {
        Text(
            text = movie.genres.joinToString { it.name },
            fontSize = DETAILS_PRIMARY_SIZE,
            color = Color.White
        )
    }
}

/**
 * The method adds a description of the movie
 * @param movie - Details of movie that comes from remote server
 */

@Composable
private fun MovieOverview(movie: MovieUI) {
    TitleCategoryDetails(
        title = stringResource(id = R.string.overview),
        modifier = Modifier.padding(top = DETAILS_PRIMARY_PAGING, bottom = DETAILS_PRIMARY_PAGING)
    )
    Text(
        text = movie.overview,
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING),
        color = Color.White
    )
}

/**
 * The method adds information about the actors of this movie to the screen
 * @param movie - Details of movie that comes from remote server
 * @param navController - To navigate actor details screen
 */

@Composable
private fun MovieCasts(movie: MovieUI, navController: NavController) {
    TitleCategoryDetails(
        title = stringResource(id = R.string.casts),
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING)
    )
    LazyRow(content = {
        itemsIndexed(movie.actors) { _, item ->
            Column(
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .clickable {
                        val actorDetailsBundle = Bundle()
                        actorDetailsBundle.putLong(ARG_ACTOR_ID, item.actorId)
                        navController.navigate(
                            ScreenState.ActorDetailsScreen.route,
                            actorDetailsBundle
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${item.profilePath}",
                    loading = { LoadingProgressBar() },
                    contentDescription = "movie_poster",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.name,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    color = Color.White
                )
                Text(text = item.character, color = Color.White, fontSize = 14.sp)
            }
        }
    })
}

/**
 * The method adds details about the movie trailer of this movie to the screen
 * @param movieDetailsData - Details of movie that comes from remote server
 */

@Composable
private fun MovieTrailer(movieDetailsData: MovieUI) {
    val context = LocalContext.current
    TitleCategoryDetails(
        title = stringResource(id = R.string.trailer),
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
    )
    Box(modifier = Modifier.size(width = 250.dp, height = 150.dp)) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDetailsData.backdropPath}",
            loading = { LoadingProgressBar() },
            contentDescription = "movie_poster",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .background(TransparentColor)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize()
                .clickable {
                    val trailerIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "http://www.youtube.com/watch?v=${
                                movieDetailsData.videos[0].key
                            })"
                        )
                    )
                    startActivity(context, trailerIntent, null)
                }) {
            Image(
                painter = painterResource(id = R.drawable.play_video),
                contentDescription = "Play Video",
                modifier = Modifier
                    .align(Center)
                    .size(64.dp)
            )
        }
    }
}

/**
 * The method adds a button to add movies to the favorites list.
 * @param movieDetailsViewModel - Details View Model
 * @param movieId - The movie id
 * @param movieFavorite - Boolean param, the param equals value in the local database by movie Id
 */

@Composable
private fun MovieFavorite(
    movieDetailsViewModel: DetailsViewModel,
    movieId: Int,
    movieFavorite: Boolean
) {
    val isFavorite = remember { mutableStateOf(movieFavorite) }
    Box(
        modifier = Modifier
            .size(25.dp)
            .clickable {
                isFavorite.value = !isFavorite.value
                movieDetailsViewModel.setFavoriteMovie(
                    movieId = movieId,
                    favorite = isFavorite.value
                )
            },
        contentAlignment = Center
    ) {
        if (isFavorite.value) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                contentDescription = ""
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                contentDescription = ""
            )
        }
    }
}

/**
 * The method for setting the name of the subcategory section on the movie details screen
 * @param title - The title of subcategory
 */

@Composable
private fun TitleCategoryDetails(title: String, modifier: Modifier) {
    Text(
        text = title,
        color = Color.White,
        modifier = modifier,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
}

/**
 * The method adds an additional list with similar movies to the movie details screen
 * @param detailsViewModel - Needed to get similar movies
 * @param movieId - The current ID of the movie that similar movies will be searched for
 * @param navController - To navigate another details screen
 */

@Composable
private fun SimilarMovies(
    detailsViewModel: DetailsViewModel,
    movieId: Int,
    navController: NavController,
    networkStatus: Boolean
) {
    TitleCategoryDetails(
        title = stringResource(id = R.string.similar),
        modifier = Modifier.padding(top = 15.dp)
    )
    val lazySimilarMovies =
        detailsViewModel.getSimilarMovies(movieId = movieId, isOnline = networkStatus)
            .collectAsLazyPagingItems()
    LazyRow(modifier = Modifier.padding(bottom = 20.dp, top = 15.dp)) {

        items(
            count = lazySimilarMovies.itemCount,
            key = {},
        ) { index ->
            lazySimilarMovies[index]?.let {
                MovieCard(
                    modifier = Modifier
                        .size(width = CARD_WIDTH_SIZE, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            val detailsMovieBundle = Bundle()
                            detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, lazySimilarMovies[index])
                            navController.navigate(
                                ScreenState.DetailsScreen.route,
                                detailsMovieBundle
                            )
                        },
                    movie = it
                )
            }
        }
    }
}