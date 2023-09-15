package com.rickandmorty.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.theme.cellWidth

@Composable
fun Thumb(mediaItem: Character?) {
    Box(
        modifier = Modifier
            .height(cellWidth)
            .width(cellWidth)
    ) {
        AsyncImage(
            model = mediaItem?.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}