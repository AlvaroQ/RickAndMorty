package com.rickandmorty.data.server

import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    // https://rickandmortyapi.com/api/character?page=1&name=rick&status=alive

    @GET("character")
    suspend fun getCharactersAsync(
        @Query("page") page: Int,
        @Query("name") nameFiltered: String?,
        @Query("gender") genderFiltered: String?,
        @Query("status") statusFiltered: String?): CharacterList

    @GET("character/{id}")
    suspend fun getCharacterByIdAsync(@Path("id") id: Int): Character
}