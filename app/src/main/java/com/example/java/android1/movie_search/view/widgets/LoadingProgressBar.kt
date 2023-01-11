package com.example.java.android1.movie_search.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.java.android1.movie_search.view.theme.BlueDark40
import com.example.java.android1.movie_search.view.theme.PrimaryColor80

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = BlueDark40
        )
    }
}