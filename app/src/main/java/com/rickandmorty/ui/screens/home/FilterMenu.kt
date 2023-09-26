package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rickandmorty.R
import com.rickandmorty.ui.theme.Transparent
import com.rickandmorty.ui.theme.paddingSmall

@Composable
fun FilterMenu(
    title: String,
    list: Array<String>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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
                contentDescription = context.getString(R.string.menu),
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