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
            item = (vm.statusFilter ?: statusArray[0]),
            section = context.getString(R.string.status_filter),
            list = statusArray,
            onClick = {
                vm.statusFilter =
                    if (it == statusArray[0]) {
                        null
                    } else {
                        it
                    }

                vm.nextPage = 1
                vm.cleanList()
                vm.findCharacters()
            },
            Modifier.weight(1f)
        )
        FilterMenu(
            item = (vm.genderFilter ?: genderArray[0]),
            section = context.getString(R.string.gender_filter),
            list = genderArray,
            onClick = {
                vm.genderFilter =
                    if (it == statusArray[0]) {
                        null
                    } else {
                        it
                    }
                vm.nextPage = 1
                vm.cleanList()
                vm.findCharacters()
            },
            Modifier.weight(1f)
        )
    }
}