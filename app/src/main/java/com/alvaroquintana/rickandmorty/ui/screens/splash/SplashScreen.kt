package com.alvaroquintana.rickandmorty.ui.screens.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alvaroquintana.rickandmorty.R
import com.alvaroquintana.rickandmorty.ui.theme.RickAndMortyTheme

@ExperimentalFoundationApi
@Composable
fun SplashScreen(onNavigate: () -> Unit) {

    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_splash))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)

    RickAndMortyTheme {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            LottieAnimation(
                composition = loadingComposition,
                progress = { loadingProgress },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .semantics { contentDescription = "Loading Animation" }
            )

            LaunchedEffect(loadingProgress) {
                if (loadingProgress >= 1f) {
                    onNavigate()
                }
            }
        }
    }
}