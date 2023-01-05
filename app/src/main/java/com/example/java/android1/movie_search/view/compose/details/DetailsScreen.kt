package com.example.java.android1.movie_search.view.compose.details

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.view.compose.theme.*
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import java.text.DecimalFormat

/**
 * The main method [DetailsScreen] that combines all the necessary methods for this screen
 */

@Composable
fun DetailsScreen(movieDataTMDB: MovieDataTMDB, navController: NavController) {
    val movieDetailsViewModel by remember {
        mutableStateOf(DetailsViewModel())
    }
    LaunchedEffect(Unit) {
        movieDataTMDB.id?.let { movieDetailsViewModel.getMovieDetailsFromRemoteSource(it, "ru-RU") }
    }
    movieDetailsViewModel.detailsLiveData.observeAsState().value?.let { state ->
        RenderDataFromRemoteServer(state, movieDetailsViewModel, navController)
    }
}

/**
 * The method processes state from the database
 * @param roomAppState - The state that came from the database. [RoomAppState]
 * @param movieDetailsViewModel - ViewModel for logic processing
 * @param movieId - The movie id
 */

@Composable
private fun RenderDataFromLocalDataBase(
    movieDetailsViewModel: DetailsViewModel,
    movieId: Int,
    roomAppState: RoomAppState
) {
    when (roomAppState) {
        is RoomAppState.Error -> FavoriteOfMovie(movieDetailsViewModel, movieId, false)
        RoomAppState.Loading -> FavoriteOfMovie(movieDetailsViewModel, movieId, false)
        is RoomAppState.Success -> FavoriteOfMovie(
            movieDetailsViewModel,
            movieId,
            roomAppState.data[0].movieFavorite ?: false
        )
    }
}

/**
 * The method processes state from the remote server
 * @param movieAppState - The state that came from the remote server. [MovieAppState]
 * @param movieDetailsViewModel - ViewModel for logic processing
 * @param navController - To navigate back
 */

@Composable
private fun RenderDataFromRemoteServer(
    movieAppState: MovieAppState,
    movieDetailsViewModel: DetailsViewModel,
    navController: NavController
) {
    when (movieAppState) {
        is MovieAppState.Error -> movieAppState.error.localizedMessage?.let { ErrorMessage(message = it) {} }
        MovieAppState.Loading -> Loader()
        is MovieAppState.Success -> {
            val movieDataTMDB = movieAppState.data
            Column(
                modifier = Modifier
                    .background(PrimaryColor80)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Header(movieDataTMDB[0], navController)
                Column(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    )
                ) {
                    DetailOfMovie(movieDataTMDB[0], movieDetailsViewModel)
                    CastsOfMovie(movieDataTMDB[0])
                    if (movieDataTMDB[0].videos?.results?.isNotEmpty() == true) {
                        TrailerOfMovie(movieDataTMDB[0])
                    }
                }
            }
        }
    }
}

/**
 * The method of adding a movie picture in the header of screen
 */

@Composable
private fun Header(movieDataTMDB: MovieDataTMDB, navController: NavController) {
    Box {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDataTMDB.poster_path}",
            loading = {
                Loader()
            },
            contentDescription = "movie_poster",
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
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
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

/**
 * The method adds detailed information about the movie
 */

@Composable
private fun DetailOfMovie(movieDataTMDB: MovieDataTMDB, movieDetailsViewModel: DetailsViewModel) {
    val favoriteState = movieDetailsViewModel.movieLocalLiveData.observeAsState().value
    val ratingFormat = DecimalFormat("#.#")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = movieDataTMDB.title ?: "",
            color = Color.White,
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
        )
        movieDataTMDB.id?.let { movieId ->
            favoriteState?.let { RenderDataFromLocalDataBase(movieDetailsViewModel, movieId, it) }
        }
    }

    LazyRow(
        modifier = Modifier.padding(
            top = DETAILS_PRIMARY_PAGING,
            bottom = DETAILS_PRIMARY_PAGING
        ), content = {
            movieDataTMDB.genres?.let {
                itemsIndexed(it) { index, item ->
                    item.name?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = DETAILS_PRIMARY_SIZE,
                            color = Color.White
                        )
                    }
                    if (index < it.size - 1) {
                        Text(text = ", ", fontSize = DETAILS_PRIMARY_SIZE, color = Color.White)
                    }
                }
            }
        })
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "IMDB ${ratingFormat.format(movieDataTMDB.vote_average)}",
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
            text = "${movieDataTMDB.release_date?.let { "".getYearFromStringFullDate(it) }}",
            modifier = Modifier.padding(end = 15.dp),
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
        Text(
            text = "${
                movieDataTMDB.runtime?.let {
                    MovieDetailsFragment().timeToFormatHoursAndMinutes(
                        it
                    )
                }
            }",
            color = Color.White,
            fontSize = DETAILS_PRIMARY_SIZE
        )
    }
    Text(
        text = "Overview",
        modifier = Modifier.padding(top = DETAILS_PRIMARY_PAGING, bottom = DETAILS_PRIMARY_PAGING),
        color = Color.White,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "${movieDataTMDB.overview}",
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING),
        color = Color.White
    )

    Text(
        text = "Casts",
        color = Color.White,
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING),
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
}

/**
 * The method adds information about the actors of this movie to the screen
 */

@Composable
private fun CastsOfMovie(movieDataTMDB: MovieDataTMDB) {
    LazyRow(content = {
        movieDataTMDB.credits?.let {
            itemsIndexed(it.cast) { _, item ->
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${item.profile_path}",
                        loading = {
                            Loader()
                        },
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
 */

@Composable
private fun TrailerOfMovie(movieDataTMDB: MovieDataTMDB) {
    val context = LocalContext.current
    Text(
        text = "Trailer",
        color = Color.White,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
    Box(modifier = Modifier.size(width = 250.dp, height = 150.dp)) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDataTMDB.backdrop_path}",
            loading = {
                Loader()
            },
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
                    val openTrailer = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "http://www.youtube.com/watch?v=${
                                movieDataTMDB.videos?.results?.get(
                                    0
                                )?.key
                            })"
                        )
                    )
                    startActivity(context, openTrailer, null)
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
 */

@Composable
private fun FavoriteOfMovie(
    movieDetailsViewModel: DetailsViewModel,
    movieId: Int,
    favorite: Boolean
) {
    val isCheck = remember { mutableStateOf(favorite) }
    Box(
        modifier = Modifier
            .size(25.dp)
            .clickable {
                isCheck.value = !isCheck.value
                movieDetailsViewModel.updateMovieFavorite(
                    movieId = movieId,
                    favorite = isCheck.value
                )
            },
        contentAlignment = Center
    ) {
        if (isCheck.value) {
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
