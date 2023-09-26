@file:OptIn(ExperimentalFoundationApi::class)

package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.rickandmorty.R
import com.rickandmorty.ui.theme.Transparent
import kotlinx.coroutines.launch

@Composable
fun TabbedView(onNavigate: (Int) -> Unit) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabs = listOf(
        context.getString(R.string.tab_character),
        context.getString(R.string.tab_favorite)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TabbedViewPager(tabs = tabs, pagerState = pagerState) { selectedTabIndex ->
            when (selectedTabIndex) {
                0 -> MediaList(onClick = { onNavigate(it.id) })
                1 -> MediaListFavorite(onClick = { onNavigate(it.id) })
            }
        }
    }
}

@Composable
fun TabbedViewPager(
    tabs: List<String>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable (selectedTabIndex: Int) -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    unselectedContentColor = MaterialTheme.colorScheme.tertiary,
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            content(page)
        }
    }
}