package com.alvaroquintana.rickandmorty.ui.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.alvaroquintana.rickandmorty.R
import com.alvaroquintana.rickandmorty.common.errorToString
import com.alvaroquintana.rickandmorty.data.Error


@Composable
fun ShowError(error: Error?) {
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(
                context,
                context.errorToString(it),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun ShowNoMoreItemFound(noMoreItemFound: Boolean) {
    val context = LocalContext.current

    LaunchedEffect(noMoreItemFound) {
        if (noMoreItemFound) {
            Toast.makeText(
                context,
                context.getString(R.string.no_more_item_found),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}