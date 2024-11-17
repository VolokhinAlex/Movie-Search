package com.volokhinaleksey.movie_club.actor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.volokhinaleksey.movie_club.actor.viewmodel.ActorsViewModel
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor80
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActorDetailsScreen(
    actorId: Long,
    actorsViewModel: ActorsViewModel = koinViewModel(),
    onBack: () -> Unit,
) {
    LaunchedEffect(key1 = true) { actorsViewModel.getActorDetails(actorId = actorId) }

    val actorDetailsState by actorsViewModel.actorDetailsState.collectAsState()

    Column(
        modifier = Modifier
            .background(PrimaryColor80)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ActorDetailsContent(
            state = actorDetailsState,
            onReloadPage = { actorsViewModel.getActorDetails(actorId = actorId) },
            onBack = onBack
        )
    }
}