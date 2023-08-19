package com.rickandmorty.managers

import com.rickandmorty.BuildConfig
import com.rickandmorty.data.source.CharacterDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerApi {
    private val baseUrl = "https://rickandmortyapi.com/api/"

    private val okHttpClient = HttpLoggingInterceptor().run {
        val builder = OkHttpClient.Builder().run {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            addInterceptor {it.proceed(it.request()) }
        }.addInterceptor(this)
        builder.build()
    }

    val service: CharacterDataSource = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run { create(CharacterDataSource::class.java) }
}