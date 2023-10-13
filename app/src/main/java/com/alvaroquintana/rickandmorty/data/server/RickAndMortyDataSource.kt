package com.alvaroquintana.rickandmorty.data.server

import com.alvaroquintana.rickandmorty.data.source.CharacterDataSource
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.domain.CharacterResult
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor() : CharacterDataSource {

    override suspend fun getCharacters(
        page: Int,
        nameFiltered: String?,
        genderFiltered: String?,
        statusFiltered: String?
    ): CharacterResult {
        return RickAndMortyApi.service
            .getCharactersAsync(
                page = page,
                nameFiltered = nameFiltered,
                genderFiltered = genderFiltered,
                statusFiltered = statusFiltered
            )
    }

    override suspend fun getCharacterById(id: Int): Character {
        return RickAndMortyApi.service
            .getCharacterByIdAsync(id = id)
    }
}