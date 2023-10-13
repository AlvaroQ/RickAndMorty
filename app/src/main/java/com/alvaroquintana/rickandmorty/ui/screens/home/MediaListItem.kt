package com.alvaroquintana.rickandmorty.ui.screens.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvaroquintana.rickandmorty.R
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.ui.composables.FavScaleAnimation
import com.alvaroquintana.rickandmorty.ui.theme.Red
import com.alvaroquintana.rickandmorty.ui.theme.RickAndMortyTheme
import com.alvaroquintana.rickandmorty.ui.theme.cellWidth
import com.alvaroquintana.rickandmorty.ui.theme.favWidth
import com.alvaroquintana.rickandmorty.ui.theme.paddingMedium
import kotlinx.coroutines.delay

const val DELAY_ANIMATED_VISIBILITY = 70L

@Suppress("LongParameterList", "LongMethod")
@Composable
fun MediaListItem(
    index: Int,
    mediaItem: Character,
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val clickEnabled = remember { mutableStateOf(true) }
    var selected by remember { mutableStateOf(false) }
    val scale = remember { Animatable(initialValue = 1f) }
    FavScaleAnimation(selected, clickEnabled, scale, mediaItem.favorite)


    val animatedVisibility = remember {
        Animatable(if (visible) 1f else 0f)
    }

    LaunchedEffect(index) {
        delay(index * DELAY_ANIMATED_VISIBILITY)
        animatedVisibility.animateTo(1f, animationSpec = tween(durationMillis = 150))
    }

    RickAndMortyTheme {
        Card(
            modifier = modifier
                .alpha(animatedVisibility.value)
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .padding(paddingMedium)
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .height(cellWidth)
                        .width(cellWidth)
                ) {
                    AsyncImage(
                        model = mediaItem.image,
                        contentDescription = context.getString(R.string.image),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // TEXT
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(paddingMedium)
                ) {
                    Text(
                        text = mediaItem.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // FAV
                Box(
                    modifier = Modifier
                        .height(cellWidth)
                        .width(cellWidth)
                ) {
                    IconButton(
                        onClick = {
                            if (clickEnabled.value) {
                                if (!mediaItem.favorite) selected = !selected
                                vm.saveFavorite(!mediaItem.favorite, mediaItem)
                            }
                        },
                        modifier = Modifier
                            .scale(scale = scale.value)
                            .height(favWidth)
                            .width(favWidth)
                    ) {
                        val icons =
                            if (mediaItem.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(
                            imageVector = icons,
                            tint = Red,
                            contentDescription = context.getString(R.string.favorite)
                        )
                    }
                }
            }
        }
    }
}