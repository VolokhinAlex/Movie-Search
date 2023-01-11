package com.example.java.android1.movie_search.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import coil.compose.SubcomposeAsyncImage
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieDataAppState
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.convertStringFullDateToOnlyYear
import com.example.java.android1.movie_search.utils.timeToFormatHoursAndMinutes
import com.example.java.android1.movie_search.view.LanguageQuery
import com.example.java.android1.movie_search.view.actor_details.ARG_ACTOR_ID
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.navigation.navigate
import com.example.java.android1.movie_search.view.theme.*
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.LoadingProgressBar
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import java.text.DecimalFormat

/**
 * The main method [DetailsScreen] that combines all the necessary methods for this screen
 * @param movieDataTMDB - Movie Data has gotten From Remote Server
 * @param navController - Controller for screen navigation
 * @param movieDetailsViewModel - Details View Model [DetailsViewModel]
 */

@Composable
fun DetailsScreen(
    movieDataTMDB: MovieDataTMDB,
    navController: NavController,
    movieDetailsViewModel: DetailsViewModel
) {
    LaunchedEffect(true) {
        movieDataTMDB.id?.let { movieId ->
            movieDetailsViewModel.getMovieDetailsFromRemoteSource(
                movieId,
                LanguageQuery.EN.languageQuery
            )
        }
    }
    movieDetailsViewModel.detailsMovieData.observeAsState().value?.let { state ->
        movieDataTMDB.id?.let { movieId ->
            RenderMovieDetailsDataFromRemoteServer(
                state, movieDetailsViewModel, navController,
                movieId
            )
        }
    }
}

/**
 * The method processes state from the database
 * @param roomAppState - The state that came from the database. [RoomAppState]
 * @param movieDetailsViewModel - ViewModel for logic processing
 * @param movieId - The movie id
 */

@Composable
private fun RenderMovieDetailsDataFromLocalDataBase(
    movieDetailsViewModel: DetailsViewModel,
    movieId: Int,
    roomAppState: RoomAppState
) {
    when (roomAppState) {
        is RoomAppState.Error -> MovieFavorite(movieDetailsViewModel, movieId, false)
        RoomAppState.Loading -> LoadingProgressBar()
        is RoomAppState.Success -> MovieFavorite(
            movieDetailsViewModel,
            movieId,
            roomAppState.data[0].movieFavorite ?: false
        )
    }
}

/**
 * The method processes state from the remote server
 * @param movieDataAppState - The state that came from the remote server. [MovieDataAppState]
 * @param movieDetailsViewModel - ViewModel for logic processing
 * @param navController - To navigate back
 * @param movieId - The movie id
 */

@Composable
private fun RenderMovieDetailsDataFromRemoteServer(
    movieDataAppState: MovieDataAppState,
    movieDetailsViewModel: DetailsViewModel,
    navController: NavController,
    movieId: Int
) {
    when (movieDataAppState) {
        is MovieDataAppState.Error -> movieDataAppState.error.localizedMessage?.let { message ->
            ErrorMessage(message = message) {
                movieDetailsViewModel.getMovieDetailsFromRemoteSource(
                    movieId,
                    LanguageQuery.EN.languageQuery
                )
            }
        }
        MovieDataAppState.Loading -> LoadingProgressBar()
        is MovieDataAppState.Success -> {
            val listOfMovies =
                movieDataAppState.data 
            Column(
                modifier = Modifier
                    .background(PrimaryColor80)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderDetailsScreen(listOfMovies, navController)
                Column(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    )
                ) {
                    MovieDetails(listOfMovies, movieDetailsViewModel)
                    MovieCasts(listOfMovies, navController)
                    if (listOfMovies.videos?.results?.isNotEmpty() == true) {
                        MovieTrailer(listOfMovies)
                    }
                }
            }
        }
    }
}

/**
 * The method of adding a movie picture in the header of screen
 * @param movieDataTMDB - Movie Data From Remote Server
 * @param navController - To navigate back
 */

@Composable
private fun HeaderDetailsScreen(movieDataTMDB: MovieDataTMDB, navController: NavController) {
    Box {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDataTMDB.poster_path}",
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
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Icon to navigate back",
                tint = Color.White
            )
        }
    }
}

/**
 * The method adds detailed information about the movie
 * @param movieDetailsData - Details of movie that comes from remote server
 * @param movieDetailsViewModel - Details View Model
 */

@Composable
private fun MovieDetails(movieDetailsData: MovieDataTMDB, movieDetailsViewModel: DetailsViewModel) {
    val movieFavoriteState = movieDetailsViewModel.movieLocalData.observeAsState().value
    val ratingFormat = DecimalFormat("#.#")
    val delimiter = ", "
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = movieDetailsData.title ?: "",
            color = Color.White,
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
        )
        movieDetailsData.id?.let { movieId ->
            movieFavoriteState?.let { favoriteState ->
                RenderMovieDetailsDataFromLocalDataBase(
                    movieDetailsViewModel = movieDetailsViewModel,
                    movieId = movieId,
                    roomAppState = favoriteState
                )
            }
        }
    }
    LazyRow(
        modifier = Modifier.padding(
            top = DETAILS_PRIMARY_PAGING,
            bottom = DETAILS_PRIMARY_PAGING
        ), content = {
            movieDetailsData.genres?.let { genres ->
                itemsIndexed(genres) { index, item ->
                    item.name?.let { genre ->
                        Text(
                            text = genre,
                            fontSize = DETAILS_PRIMARY_SIZE,
                            color = Color.White
                        )
                    }
                    if (index < genres.size - 1) {
                        Text(text = delimiter, fontSize = DETAILS_PRIMARY_SIZE, color = Color.White)
                    }
                }
            }
        })
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "IMDB ${ratingFormat.format(movieDetailsData.vote_average)}",
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
            text = "${movieDetailsData.release_date?.let { convertStringFullDateToOnlyYear(it) }}",
            modifier = Modifier.padding(end = 15.dp),
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
        Text(
            text = "${movieDetailsData.runtime?.let { timeToFormatHoursAndMinutes(it) }}",
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
    }
    SetSubCategoryTitle(
        title = stringResource(id = R.string.overview),
        modifier = Modifier.padding(top = DETAILS_PRIMARY_PAGING, bottom = DETAILS_PRIMARY_PAGING)
    )
    Text(
        text = "${movieDetailsData.overview}",
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING),
        color = Color.White
    )
    SetSubCategoryTitle(
        title = stringResource(id = R.string.casts),
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING)
    )
}

/**
 * The method adds information about the actors of this movie to the screen
 * @param movieDetailsData - Details of movie that comes from remote server
 * @param navController - To navigate actor details screen
 */

@Composable
private fun MovieCasts(movieDetailsData: MovieDataTMDB, navController: NavController) {
    LazyRow(content = {
        movieDetailsData.credits?.let { credits ->
            itemsIndexed(credits.cast) { _, item ->
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .clickable {
                            val actorDetailsBundle = Bundle()
                            item.id?.let { actorId ->
                                actorDetailsBundle.putLong(ARG_ACTOR_ID, actorId)
                                navController.navigate(
                                    ScreenState.ActorDetailsScreen.route,
                                    actorDetailsBundle
                                )
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${item.profile_path}",
                        loading = { LoadingProgressBar() },
                        contentDescription = "movie_poster",
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "${item.name}",
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        color = Color.White
                    )
                    Text(text = "${item.character}", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    })
}

/**
 * The method adds details about the movie trailer of this movie to the screen
 * @param movieDetailsData - Details of movie that comes from remote server
 */

@Composable
private fun MovieTrailer(movieDetailsData: MovieDataTMDB) {
    val context = LocalContext.current
    SetSubCategoryTitle(
        title = stringResource(id = R.string.trailer),
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
    )
    Box(modifier = Modifier.size(width = 250.dp, height = 150.dp)) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDetailsData.backdrop_path}",
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
                                movieDetailsData.videos?.results?.get(
                                    0
                                )?.key
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
private fun SetSubCategoryTitle(title: String, modifier: Modifier) {
    Text(
        text = title,
        color = Color.White,
        modifier = modifier,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
}