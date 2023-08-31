package com.rickandmorty.data.server

import com.rickandmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyApi {
    private const val baseUrl = "https://rickandmortyapi.com/api/"

    private val okHttpClient = HttpLoggingInterceptor().run {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    val service: RickAndMortyService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run { create(RickAndMortyService::class.java) }
}