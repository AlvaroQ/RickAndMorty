package com.alvaroquintana.rickandmorty.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.alvaroquintana.rickandmorty.R
import com.alvaroquintana.rickandmorty.common.TAB_CHARACTERS
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.ui.composables.ShowError
import com.alvaroquintana.rickandmorty.ui.composables.ShowNoMoreItemFound
import com.alvaroquintana.rickandmorty.ui.theme.paddingXsmall
import kotlinx.coroutines.delay

const val DELAY_TO_SHOW_CARD_ANIMATION = 200L

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
    resIdNoItemsFound: Int,
    vm: HomeViewModel = hiltViewModel()
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
            itemsIndexed(list) { index, item ->
                MediaListItem(
                    index = index,
                    mediaItem = item,
                    visible = index < vm.visibleCards,
                    onClick = { onClick(item) },
                    modifier = Modifier.padding(paddingXsmall)
                )

                LaunchedEffect(vm.visibleCards) {
                    if (vm.visibleCards < list.size) {
                        delay(DELAY_TO_SHOW_CARD_ANIMATION)
                        vm.visibleCards++
                    }
                }
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

