package com.rickandmorty.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.rickandmorty.R
import com.rickandmorty.data.Error


inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun Context.errorToString(error: Error) = when(error) {
    Error.Connectivity -> getString(R.string.error_connectivity)
    is Error.Server -> getString(R.string.error_server) + error.code
    is Error.Unknown -> getString(R.string.error_unknown) + error.message
}