package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    RickAndMortyTheme {
        Scaffold(
            topBar = { MainAppBar() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                FilterRow()
                TabbedView(onNavigate = onNavigate)
            }
        }
    }
}