package com.alvaroquintana.rickandmorty.data.source

import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.domain.CharacterResult

interface CharacterDataSource {

    suspend fun getCharacters(
        page: Int,
        nameFiltered: String?,
        genderFiltered: String?,
        statusFiltered: String?
    ): CharacterResult

    suspend fun getCharacterById(id: Int): Character
}