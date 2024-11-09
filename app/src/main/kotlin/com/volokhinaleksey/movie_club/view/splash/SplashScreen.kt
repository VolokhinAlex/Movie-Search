package com.volokhinaleksey.movie_club.view.splash

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volokhinaleksey.movie_club.R
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor80
import kotlinx.coroutines.delay

/**
 * Application Start Screen
 * @param navController - object for working with navigation
 */

@Composable
fun SplashScreen(navController: NavController) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val animationAlpha = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutLinearInEasing
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(ScreenState.HomeScreen.route)
    }

    Splash(alpha = animationAlpha.value)
}

@Composable
fun Splash(alpha: Float) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Application logotype",
            modifier = Modifier
                .size(200.dp)
                .alpha(alpha = alpha),
            tint = Color.White
        )

    }

}