package com.example.java.android1.movie_search.view.actor_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieActorAppState
import com.example.java.android1.movie_search.model.ActorDTO
import com.example.java.android1.movie_search.view.LanguageQuery
import com.example.java.android1.movie_search.view.theme.DETAILS_PRIMARY_SIZE
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.LoadingProgressBar
import com.example.java.android1.movie_search.viewmodel.MovieActorViewModel

const val ARG_ACTOR_ID = "actor id"

/**
 * The main method [ActorDetailsScreen] that combines all the necessary methods for this screen
 * @param actorId - actor id has gotten from remote server
 * @param navController - Controller for screen navigation
 * @param actorViewModel - Actor View Model [MovieActorViewModel]
 */

@Composable
fun ActorDetailsScreen(
    actorId: Long,
    navController: NavController,
    actorViewModel: MovieActorViewModel
) {
    LaunchedEffect(key1 = true) {
        actorViewModel.getMovieActorData(actorId, LanguageQuery.EN.languageQuery)
    }
    actorViewModel.movieActorData.observeAsState().value?.let { actorState ->
        RenderActorDataFromRemoteServer(actorState, navController)
    }
}

/**
 * The method processes state from the remote server
 * @param movieActorAppState - The state that came from the remote server. [MovieActorAppState]
 * @param navController - To navigate back
 */

@Composable
fun RenderActorDataFromRemoteServer(
    movieActorAppState: MovieActorAppState,
    navController: NavController
) {
    when (movieActorAppState) {
        is MovieActorAppState.Error -> movieActorAppState.error.localizedMessage?.let {
            ErrorMessage(
                message = it
            ) {}
        }
        MovieActorAppState.Loading -> LoadingProgressBar()
        is MovieActorAppState.Success -> {
            val actorDTO = movieActorAppState.data
            Column(
                modifier = Modifier
                    .background(PrimaryColor80)
                    .fillMaxSize()
            ) {
                HeaderActorDetailsScreen(navController = navController)
                ActorDetails(actorDTO)
            }
        }
    }
}

/**
 * The method sets the title of the screen and adds a button to go to the previous screen
 * @param navController - Needed to navigate back
 */

@Composable
fun HeaderActorDetailsScreen(navController: NavController) {
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
    }
}

/**
 * The method adds detailed information about the actor
 */

@Composable
fun ActorDetails(actorDTO: ActorDTO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${actorDTO.profile_path}",
            loading = { LoadingProgressBar() },
            contentDescription = "actor_photo",
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            contentScale = ContentScale.Crop
        )
        actorDTO.name?.let { name ->
            Text(
                text = name,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = TITLE_SIZE,
                color = Color.White
            )
        }
        actorDTO.birthday?.let { birthday ->
            Text(
                text = "${stringResource(id = R.string.birthday)}: $birthday",
                modifier = Modifier.padding(top = 10.dp),
                fontSize = DETAILS_PRIMARY_SIZE,
                color = Color.White
            )
        }
        actorDTO.biography?.let { biography ->
            Text(
                text = "${stringResource(id = R.string.biography)}: $biography",
                modifier = Modifier.padding(top = 10.dp),
                fontSize = DETAILS_PRIMARY_SIZE,
                color = Color.White
            )
        }
    }
}
