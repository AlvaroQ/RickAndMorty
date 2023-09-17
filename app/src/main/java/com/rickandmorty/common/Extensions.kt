package com.rickandmorty.common

import android.content.Context
import com.rickandmorty.R
import com.rickandmorty.data.Error


fun Context.errorToString(error: Error) = when (error) {
    Error.Connectivity -> getString(R.string.error_connectivity)
    is Error.Server -> getString(R.string.error_server) + error.code
    is Error.Unknown -> getString(R.string.error_unknown) + error.message
}