package com.rickandmorty.data.source

import retrofit2.http.GET
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterDataSource {
    // https://rickandmortyapi.com/api/character?page=1&name=rick&status=alive

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") nameFiltered: String?,
        @Query("gender") genderFiltered: String?,
        @Query("status") statusFiltered: String?): CharacterList

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}