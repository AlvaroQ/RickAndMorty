package com.rickandmorty.common

import android.content.Context
import androidx.core.app.ShareCompat
import com.rickandmorty.CharactersApp
import com.rickandmorty.R
import com.rickandmorty.data.Error


fun Context.errorToString(error: Error) = when (error) {
    Error.Connectivity -> getString(R.string.error_connectivity)
    is Error.Server -> getString(R.string.error_server) + error.code
    is Error.Unknown -> getString(R.string.error_unknown) + error.message
}


const val TAB_CHARACTERS = 0
const val TAB_FAVORITES = 1
fun Context.listOfTabs() =
    listOf(getString(R.string.tab_character), getString(R.string.tab_favorite))

fun Context.shareCharacter(name: String, description: String) {
    val intent = ShareCompat
        .IntentBuilder(this)
        .setType("text/plain")
        .setSubject(name)
        .setText(description)
        .intent
    startActivity(intent)
}

val Context.app
    get() = applicationContext as CharactersApp