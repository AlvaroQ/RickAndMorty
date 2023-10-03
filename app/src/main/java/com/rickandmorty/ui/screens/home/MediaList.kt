package com.rickandmorty.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R
import com.rickandmorty.common.TAB_CHARACTERS
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.composables.ShowError
import com.rickandmorty.ui.composables.ShowNoMoreItemFound
import com.rickandmorty.ui.theme.paddingXsmall


@SuppressLint("FrequentlyChangedStateReadInComposition")
@ExperimentalFoundationApi
@Composable
fun MediaList(
    selectedTabIndex: Int,
    onClick: (Character) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val state by vm.state.collectAsState()
    val favList by vm.favState.collectAsState()
    val characterList = state.characterList ?: emptyList()

    if (selectedTabIndex == TAB_CHARACTERS) {
        LoadList(onClick, listState, characterList, R.string.tab_character_no_items)
        LoadMoreItem(state.loading, listState.layoutInfo)
        ShowNoMoreItemFound(state.noMoreItemFound)
    } else {
        LoadList(onClick, listState, favList, R.string.tab_fav_no_items)
    }

    ShowError(state.error)
    LaunchedEffect(favList) {
        vm.reloadListWithFav()
    }
}

@Composable
fun LoadList(
    onClick: (Character) -> Unit,
    listState: LazyListState,
    list: List<Character>,
    resIdNoItemsFound: Int
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(paddingXsmall)
    ) {
        if (list.isEmpty()) {
            item {
                Text(
                    text = context.getString(resIdNoItemsFound),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp
                )
            }
        } else {
            items(list) {
                MediaListItem(
                    mediaItem = it,
                    onClick = { onClick(it) },
                    modifier = Modifier.padding(paddingXsmall)
                )
            }
        }
    }
}

@Composable
fun LoadMoreItem(
    isLoading: Boolean,
    layoutInfo: LazyListLayoutInfo,
    vm: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(layoutInfo) {
        val totalItems = layoutInfo.totalItemsCount
        val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

        if (lastVisibleItemIndex == totalItems - 1 && !isLoading) {
            vm.updateList()
        }
    }
}

