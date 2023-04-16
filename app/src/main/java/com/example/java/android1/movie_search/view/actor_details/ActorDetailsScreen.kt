package com.example.java.android1.movie_search.view.actor_details

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.java.android1.movie_search.model.state.ActorState
import com.example.java.android1.movie_search.model.ui.ActorUI
import com.example.java.android1.movie_search.view.LanguageQuery
import com.example.java.android1.movie_search.view.theme.DETAILS_PRIMARY_SIZE
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.LoadingProgressBar
import com.example.java.android1.movie_search.viewmodel.MovieActorViewModel
import org.koin.androidx.compose.koinViewModel

const val ARG_ACTOR_ID = "ActorID"

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
    actorViewModel: MovieActorViewModel = koinViewModel(),
    networkStatus: Boolean
) {
    LaunchedEffect(key1 = true) {
        actorViewModel.getMovieActorData(
            personId = actorId,
            language = LanguageQuery.EN.languageQuery,
            isOnline = networkStatus
        )
    }
    actorViewModel.movieActorData.observeAsState().value?.let { actorState ->
        RenderActorDataFromRemoteServer(
            actorState = actorState,
            navController = navController,
            actorViewModel = actorViewModel,
            actorId = actorId,
            networkStatus = networkStatus
        )
    }
}

/**
 * The method processes state from the remote server
 * @param actorState - The state that came from the remote server. [ActorState]
 * @param navController - To navigate back
 * @param actorViewModel - Actor View Model [MovieActorViewModel]
 * @param actorId - actor id has gotten from remote server
 */

@Composable
fun RenderActorDataFromRemoteServer(
    actorState: ActorState,
    navController: NavController,
    actorViewModel: MovieActorViewModel,
    actorId: Long,
    networkStatus: Boolean
) {
    when (actorState) {
        is ActorState.Error -> actorState.errorMessage.localizedMessage?.let { message ->
            ErrorMessage(message = message) {
                actorViewModel.getMovieActorData(
                    personId = actorId,
                    language = LanguageQuery.EN.languageQuery,
                    isOnline = networkStatus
                )
            }
        }

        ActorState.Loading -> LoadingProgressBar()
        is ActorState.Success -> {
            val actorDetailsData = actorState.data[0]
            Column(
                modifier = Modifier
                    .background(PrimaryColor80)
                    .fillMaxSize()
            ) {
                ToolbarActorDetailsScreen(navController = navController)
                ActorDetails(actorDetailsData = actorDetailsData)
            }
        }
    }
}

/**
 * The method sets the title of the screen and adds a button to go to the previous screen
 * @param navController - Needed to navigate back
 */

@Composable
fun ToolbarActorDetailsScreen(navController: NavController) {
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
                contentDescription = Icons.Default.ArrowBack.name,
                tint = Color.White
            )
        }
    }
}

/**
 * The method adds detailed information about the actor
 * @param actorDetailsData - Details about a specific actor
 */

@Composable
fun ActorDetails(actorDetailsData: ActorUI) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${actorDetailsData.profilePath}",
            loading = { LoadingProgressBar() },
            contentDescription = "actor_photo",
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = actorDetailsData.name,
            modifier = Modifier.padding(top = 10.dp),
            fontSize = TITLE_SIZE,
            color = Color.White
        )
        ActorDetailsTitleCategory(title = R.string.birthday, details = actorDetailsData.birthday)
        ActorDetailsTitleCategory(title = R.string.birthday, details = actorDetailsData.biography)
    }
}

/**
 * The method sets the title and details about actor from remote server
 * of the section in actor details screen
 * @param title - Title of information about the actor
 * @param details - Specific information about the actor. see [ActorUI]
 */

@Composable
private fun ActorDetailsTitleCategory(@StringRes title: Int, details: String) {
    Text(
        text = "${stringResource(id = title)}: $details",
        modifier = Modifier.padding(top = 10.dp),
        fontSize = DETAILS_PRIMARY_SIZE,
        color = Color.White
    )
}