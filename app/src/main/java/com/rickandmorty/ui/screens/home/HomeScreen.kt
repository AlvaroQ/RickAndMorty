@file:OptIn(ExperimentalFoundationApi::class)

package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R
import com.rickandmorty.ui.theme.RickAndMortyTheme
import com.rickandmorty.ui.theme.Transparent
import com.rickandmorty.ui.theme.paddingSmall


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
                DataList(onNavigate = onNavigate)
            }
        }
    }
}

@Composable
fun FilterRow(vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val statusArray = stringArrayResource(R.array.status_list)
    val genderArray = stringArrayResource(R.array.gender_list)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    ) {
        MenuFilter(
            context.getString(R.string.status_filter),
            statusArray,
            Modifier.weight(1f),
            onClick = {
                vm.nextPage = 1
                vm.statusFilter = if (it == statusArray[0]) null else it
                vm.cleanList()
                vm.findCharacters()
            }
        )
        MenuFilter(
            context.getString(R.string.gender_filter),
            genderArray,
            Modifier.weight(1f),
            onClick = {
                vm.nextPage = 1
                vm.genderFilter = if (it == statusArray[0]) null else it
                vm.cleanList()
                vm.findCharacters()
            }
        )
    }
}

@Composable
fun MenuFilter(title: String, list: Array<String>, modifier: Modifier, onClick: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(list[0]) }

    Column(modifier = modifier) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Transparent,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .padding(start = paddingSmall, end = paddingSmall)
                .fillMaxWidth(),
            onClick = { expanded.value = true }
        ) {
            Text(selectedItem.value + " " + title)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp)
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem.value = item
                        expanded.value = false
                        onClick(item)
                    })
            }
        }
    }

}

@Composable
fun DataList(onNavigate: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MediaList(onClick = { onNavigate(it.id) })
    }
}