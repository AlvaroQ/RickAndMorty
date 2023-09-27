package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R


@Composable
fun FilterRow(vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val statusArray = stringArrayResource(R.array.status_list)
    val genderArray = stringArrayResource(R.array.gender_list)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FilterMenu(
            context.getString(R.string.status_filter),
            statusArray,
            onClick = {
                vm.statusFilter = if (it == statusArray[0]) null else it
                vm.nextPage = 1
                vm.cleanList()
                vm.findCharacters()
            },
            Modifier.weight(1f)
        )
        FilterMenu(
            context.getString(R.string.gender_filter),
            genderArray,
            onClick = {
                vm.genderFilter = if (it == statusArray[0]) null else it
                vm.nextPage = 1
                vm.cleanList()
                vm.findCharacters()
            },
            Modifier.weight(1f)
        )
    }
}