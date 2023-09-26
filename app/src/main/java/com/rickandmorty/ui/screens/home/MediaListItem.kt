package com.rickandmorty.ui.screens.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rickandmorty.R
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.theme.Red
import com.rickandmorty.ui.theme.RickAndMortyTheme
import com.rickandmorty.ui.theme.cellWidth
import com.rickandmorty.ui.theme.favWidth
import com.rickandmorty.ui.theme.paddingMedium

@Composable
fun MediaListItem(
    mediaItem: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    RickAndMortyTheme {
        Card(
            modifier = modifier.clickable { onClick() }
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
                        onClick = { vm.saveFavorite(mediaItem.favorite, mediaItem) },
                        modifier = Modifier
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