package com.rickandmorty.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R
import com.rickandmorty.common.errorToString
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.ComposeMainActivity
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
        modifier = Modifier.fillMaxSize(),
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
fun MediaListFavorite(
    onClick: (Character) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val favList by vm.favState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(paddingXsmall)
    ) {
        items(favList) {
            MediaListItem(
                mediaItem = it,
                onClick = { onClick(it) },
                modifier = Modifier.padding(paddingXsmall)
            )
        }
    }
}