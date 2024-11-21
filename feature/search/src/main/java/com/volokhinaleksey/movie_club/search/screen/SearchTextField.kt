package com.volokhinaleksey.movie_club.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
internal fun SearchTextField(
    query: String,
    focused: Boolean,
    onQueryChange: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    Surface(
        modifier = Modifier
            .height(56.dp)
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = if (!focused) 16.dp else 0.dp,
                end = 16.dp
            ),
        color = MovieClubTheme.colors.secondaryColor,
        shape = RoundedCornerShape(percent = 50),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (query.isEmpty()) {
                Text(
                    text = stringResource(R.string.search_hint),
                    style = MovieClubTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp),
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { onSearchFocusChange(it.isFocused) }
                    .focusRequester(focusRequester)
                    .align(Alignment.CenterStart)
                    .padding(start = 24.dp, end = 8.dp),
                textStyle = MovieClubTheme.typography.bodyMedium,
                singleLine = true,
                cursorBrush = SolidColor(MovieClubTheme.colors.onPrimaryColor)
            )
        }
    }
}