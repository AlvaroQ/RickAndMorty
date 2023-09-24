package com.rickandmorty.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rickandmorty.R
import com.rickandmorty.common.errorToString
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.ComposeMainActivity
import com.rickandmorty.ui.theme.Red
import com.rickandmorty.ui.theme.RickAndMortyTheme
import com.rickandmorty.ui.theme.cellWidth
import com.rickandmorty.ui.theme.favWidth
import com.rickandmorty.ui.theme.paddingMedium
import com.rickandmorty.ui.theme.paddingXsmall
import kotlinx.coroutines.delay

@SuppressLint("FrequentlyChangedStateReadInComposition")
@ExperimentalFoundationApi
@Composable
fun MediaList(
    onClick: (Character) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var loadingBlocked by remember { mutableStateOf(false) }
    val state by vm.state.collectAsState()
    val characterList = state.characterList ?: emptyList()
    val error = state.error
    val noMoreItemFound = state.noMoreItemFound

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(paddingXsmall)
    ) {
        items(characterList) {
            MediaListItem(
                mediaItem = it,
                onClick = { onClick(it) },
                modifier = Modifier.padding(paddingXsmall)
            )
        }
    }

    LaunchedEffect(listState.layoutInfo) {
        val totalItems = listState.layoutInfo.totalItemsCount
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

        if (lastVisibleItemIndex == totalItems - 1 && !loadingBlocked) {
            loadingBlocked = true
            vm.updateList()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(
                context,
                context.errorToString(it),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(noMoreItemFound) {
        if (noMoreItemFound) {
            Toast.makeText(
                context,
                context.getString(R.string.no_more_item_found),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(state) {
        delay(ComposeMainActivity.DELAY_TO_REFRESH_LIST)
        loadingBlocked = false
    }
}

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