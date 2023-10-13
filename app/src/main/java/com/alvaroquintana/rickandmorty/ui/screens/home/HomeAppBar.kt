package com.alvaroquintana.rickandmorty.ui.screens.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.alvaroquintana.rickandmorty.R
import com.alvaroquintana.rickandmorty.ui.theme.paddingMedium

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(vm: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val title: String = vm.nameFilter ?: stringResource(R.string.app_name)
    var searchText by remember { mutableStateOf(title) }
    var isSearchExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            BasicTextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        isSearchExpanded = !isSearchExpanded

                        keyboardController?.hide()
                        focusManager.clearFocus()

                        vm.visibleCards = 0
                        vm.nextPage = 1
                        vm.nameFilter = searchText
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
                    if (isSearchExpanded) {
                        // RESET
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        searchText = context.getString(R.string.app_name)

                        vm.nextPage = 1
                        vm.nameFilter = null
                        vm.cleanList()
                        vm.findCharacters()
                    } else {
                        // SEARCH
                        if (searchText == context.getString(R.string.app_name)) {
                            searchText = ""
                        }
                        keyboardController?.show()
                        focusRequester.requestFocus()
                    }

                    isSearchExpanded = !isSearchExpanded
                }) {

                val contentString = if (isSearchExpanded)
                    context.getString(R.string.close)
                else
                    context.getString(R.string.search)

                Icon(
                    imageVector = if (isSearchExpanded) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = contentString
                )
            }
        }
    )
}