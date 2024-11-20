package com.volokhinaleksey.movie_club.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
internal fun SearchBar(
    query: String,
    focused: Boolean,
    onQueryChange: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = focused) {
            IconButton(
                modifier = Modifier.padding(start = 2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onQueryChange("")
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MovieClubTheme.colors.onPrimaryColor
                )
            }
        }

        SearchTextField(
            query = query,
            focused = focused,
            onQueryChange = { onQueryChange(it) },
            onSearchFocusChange = onSearchFocusChange,
        )
    }
}