package com.alvaroquintana.rickandmorty.data.server

import com.alvaroquintana.rickandmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyApi {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val okHttpClient = HttpLoggingInterceptor().run {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    val service: RickAndMortyService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run { create(RickAndMortyService::class.java) }
}