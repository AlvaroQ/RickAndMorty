package com.rickandmorty.data.source

import retrofit2.http.GET
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import retrofit2.Response
import retrofit2.http.Path

interface CharacterDataSource {

    @GET("character")
    suspend fun getCharacters(): CharacterList


    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}