package com.rickandmorty

import android.app.Application

class CharactersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}