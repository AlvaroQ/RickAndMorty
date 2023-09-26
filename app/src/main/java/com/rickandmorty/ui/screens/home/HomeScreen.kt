package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rickandmorty.ui.composables.CollapsingLayout
import com.rickandmorty.ui.theme.RickAndMortyTheme

@ExperimentalFoundationApi
@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    RickAndMortyTheme {
        Scaffold(
            topBar = { MainAppBar() },
            content = { padding ->
                CollapsingLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    collapsingTop = { FilterRow() },
                    bodyContent = { TabbedView(onNavigate = onNavigate) }
                )
            }
        )
    }
}