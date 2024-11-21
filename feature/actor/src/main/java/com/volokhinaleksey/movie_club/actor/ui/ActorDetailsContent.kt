package com.volokhinaleksey.movie_club.actor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.movie_club.model.state.ActorDetailsState
import com.volokhinaleksey.movie_club.model.ui.Actor
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme
import com.volokhinaleksey.movie_club.uikit.widgets.ErrorMessage
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.utils.TMDB_LOAD_IMAGE_API

@Composable
internal fun ActorDetailsContent(
    state: ActorDetailsState,
    onReloadPage: () -> Unit,
    onBack: () -> Unit,
) {
    when (state) {
        ActorDetailsState.Loading -> LoadingProgressBar()
        is ActorDetailsState.Error -> ErrorMessage(message = state.msg, click = onReloadPage)
        is ActorDetailsState.Success -> {
            Row(
                modifier = Modifier.padding(top = 20.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = MovieClubTheme.colors.onPrimaryColor
                    )
                }
            }

            ActorDetails(actor = state.details)
        }
    }
}

@Composable
internal fun ActorDetails(actor: Actor) {
    val detailItems = remember {
        listOf(
            Pair(R.string.birthday, actor.birthday),
            Pair(R.string.biography, actor.biography),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = "$TMDB_LOAD_IMAGE_API${actor.profilePath}",
            loading = { LoadingProgressBar() },
            contentDescription = "actor_photo",
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = actor.name,
            modifier = Modifier.padding(top = 15.dp),
            style = MovieClubTheme.typography.headingLarge
        )

        detailItems.forEach {
            Text(
                text = "${stringResource(id = it.first)}: ${it.second}",
                modifier = Modifier.padding(top = 15.dp),
                style = MovieClubTheme.typography.bodyMedium
            )
        }
    }
}