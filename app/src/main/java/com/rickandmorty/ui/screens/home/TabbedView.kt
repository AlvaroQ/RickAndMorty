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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.common.listOfTabs
import com.rickandmorty.ui.composables.CenteredCircularProgressIndicator
import com.rickandmorty.ui.theme.Transparent
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun TabbedView(onNavigate: (Int) -> Unit, vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 2 })
    val state by vm.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TabbedViewPager(tabs = context.listOfTabs(), pagerState = pagerState) { selectedTabIndex ->
            vm.selectedTabIndex = selectedTabIndex
            MediaList(selectedTabIndex = selectedTabIndex, onClick = { onNavigate(it.id) })
        }
    }
    CenteredCircularProgressIndicator(state.loading)
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