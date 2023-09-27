package com.rickandmorty.common

import android.content.Context
import com.rickandmorty.CharactersApp
import com.rickandmorty.R
import com.rickandmorty.data.Error


fun Context.errorToString(error: Error) = when (error) {
    Error.Connectivity -> getString(R.string.error_connectivity)
    is Error.Server -> getString(R.string.error_server) + error.code
    is Error.Unknown -> getString(R.string.error_unknown) + error.message
}

fun Context.listOfTabs() =
    listOf(getString(R.string.tab_character), getString(R.string.tab_favorite))

val Context.app
    get() = applicationContext as CharactersApp