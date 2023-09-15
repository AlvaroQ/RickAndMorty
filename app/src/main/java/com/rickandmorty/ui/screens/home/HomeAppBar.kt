package com.rickandmorty.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R
import com.rickandmorty.ui.theme.paddingMedium

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainAppBar(vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val title: String = stringResource(R.string.app_name)
    val searchText = remember { mutableStateOf(title) }
    val isSearchExpanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            BasicTextField(
                value = searchText.value,
                onValueChange = { newText ->
                    searchText.value = newText
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        isSearchExpanded.value = !isSearchExpanded.value

                        keyboardController?.hide()
                        focusManager.clearFocus()

                        vm.nextPage = 1
                        vm.nameFilter = searchText.value
                        vm.cleanList()
                        vm.findCharacters()
                    }
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxSize(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
                singleLine = true,
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = paddingMedium),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                    }
                }
            )

        },
        actions = {
            IconButton(
                onClick = {
                    if (isSearchExpanded.value) {
                        // RESET
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        searchText.value = context.getString(R.string.app_name)

                        vm.nextPage = 1
                        vm.nameFilter = null
                        vm.cleanList()
                        vm.findCharacters()
                    } else {
                        // SEARCH
                        if (searchText.value == context.getString(R.string.app_name)) {
                            searchText.value = ""
                        }
                        keyboardController?.show()
                        focusRequester.requestFocus()
                    }

                    isSearchExpanded.value = !isSearchExpanded.value
                }) {
                Icon(
                    imageVector = if (isSearchExpanded.value) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if (isSearchExpanded.value) "Close" else "Search"
                )
            }
        }
    )
}