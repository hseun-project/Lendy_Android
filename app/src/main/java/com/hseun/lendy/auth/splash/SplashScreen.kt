package com.hseun.lendy.auth.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy.R
import com.hseun.lendy.auth.splash.viewmodel.SplashViewModel
import com.hseun.lendy.ui.theme.Main
import kotlinx.coroutines.launch

const val NAVIGATION_SPLASH = "splash"

@Composable
fun LendySplashScreen(
    modifier: Modifier = Modifier,
    viewModel :SplashViewModel = hiltViewModel(),
    navToMain: () -> Unit,
    navToSignIn: () -> Unit
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }

    val isChecked = viewModel.isAutoLoginCheck
    val isSuccess = viewModel.isAutoLoginSuccess

    LaunchedEffect(Unit) {
        viewModel.autoLogin()
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Main)
    ) {
        Image(
            modifier = modifier
                .align(Alignment.Center)
                .padding(
                    start = 70.dp,
                    end = 70.dp,
                    bottom = 40.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            painter = painterResource(id = R.drawable.lendy_logo_full),
            contentDescription = "Lendy Logo",
            contentScale = ContentScale.FillWidth
        )
    }

    LaunchedEffect(isChecked) {
        if (isSuccess) {
            navToMain()
        } else {
            navToSignIn()
        }
    }
}