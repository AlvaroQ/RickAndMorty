package com.rickandmorty.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.text.buildSpannedString
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rickandmorty.R
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.composables.ArrowBackIcon
import com.rickandmorty.ui.theme.RickAndMortyTheme
import com.rickandmorty.ui.theme.descriptionHeight
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun DetailScreen(characterId: Int, onUpClick: () -> Unit, vm: DetailViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        vm.findCharacter(characterId = characterId)
    }
    val state by vm.state.collectAsState()
    val character = state.character
    val scrollState = rememberScrollState()

    RickAndMortyTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = character?.name ?: "") },
                    navigationIcon = { ArrowBackIcon(onUpClick) }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    model = character?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(descriptionHeight)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    character?.let { character ->
                        fillContent(character)
                    }
                }
            }
        }
    }
}

@Composable
private fun fillContent(character: Character) {
    val context = LocalContext.current

    character.apply {
        val text = buildSpannedString {
            appendLine(context.getString(R.string.gender) + " " + gender + "\n")
            appendLine(context.getString(R.string.status) + " " + status + "\n")
            appendLine(context.getString(R.string.species) + " " + species + "\n")
            if (type.isNotEmpty()) appendLine(context.getString(R.string.type) + " " + type + "\n")
            appendLine(context.getString(R.string.url) + " " + url + "\n")
            appendLine(context.getString(R.string.created) + " " + parseDateString(created) + "\n")
            appendLine(context.getString(R.string.location) + " " + location.name + "\n")
            appendLine(context.getString(R.string.episodes) + " " + episode.map {
                it?.toUri()?.path?.split("/")?.last()
            })
        }.toString()
        Text(text = text)
    }
}

@SuppressLint("SimpleDateFormat")
private fun parseDateString(dateString: String): String? {
    val patternInput = "yyyy-MM-dd'T'HH:mm:ss"
    val patternOutput = "yyyy-MM-dd HH:mm:ss"

    val sdf = SimpleDateFormat(patternInput)
    val output = SimpleDateFormat(patternOutput)
    val d: Date = sdf.parse(dateString)!!
    return output.format(d)
}