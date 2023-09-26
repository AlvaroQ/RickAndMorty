package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R


@Composable
fun FilterRow(vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var filterClicked by remember { mutableStateOf<Filter?>(null) }
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
                filterClicked = Filter.STATUS
            },
            Modifier.weight(1f)
        )
        FilterMenu(
            context.getString(R.string.gender_filter),
            genderArray,
            onClick = {
                vm.genderFilter = if (it == statusArray[0]) null else it
                filterClicked = Filter.GENDER
            },
            Modifier.weight(1f)
        )
    }

    filterClicked?.let { FilterMenuClicked(vm) }
}

@Composable
fun FilterMenuClicked(vm: HomeViewModel = hiltViewModel()) {
    vm.nextPage = 1
    vm.cleanList()
    vm.findCharacters()
}

enum class Filter {
    STATUS, GENDER
}