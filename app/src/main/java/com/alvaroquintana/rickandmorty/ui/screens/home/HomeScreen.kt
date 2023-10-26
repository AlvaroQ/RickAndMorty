package com.alvaroquintana.rickandmorty.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alvaroquintana.rickandmorty.ui.composables.CollapsingLayout

@ExperimentalFoundationApi
@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
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