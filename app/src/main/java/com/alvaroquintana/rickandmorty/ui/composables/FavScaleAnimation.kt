package com.alvaroquintana.rickandmorty.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.launch

@Composable
fun FavScaleAnimation(
    selected: Boolean,
    clickEnabled: MutableState<Boolean>,
    scale: Animatable<Float, AnimationVector1D>,
    isFavorite: Boolean
) {
    LaunchedEffect(selected) {
        if (selected && !isFavorite) {
            clickEnabled.value = false

            launch {
                scale.animateTo(
                    targetValue = 2f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }.join()
            clickEnabled.value = true
        }
    }
}