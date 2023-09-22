package com.rickandmorty.data.source

import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterResult

interface CharacterDataSource {

    suspend fun getCharacters(
        page: Int,
        nameFiltered: String?,
        genderFiltered: String?,
        statusFiltered: String?
    ): CharacterResult

    suspend fun getCharacterById(id: Int): Character
}