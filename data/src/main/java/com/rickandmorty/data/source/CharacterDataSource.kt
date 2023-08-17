package com.rickandmorty.data.source

import retrofit2.http.GET
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import retrofit2.Response

interface CharacterDataSource {

    @GET("character")
    suspend fun getCharacters(): CharacterList


    @GET("character/{id}")
    suspend fun getCharacterById(id: Int): Character
}