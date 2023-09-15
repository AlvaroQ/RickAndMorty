package com.rickandmorty.data.server

import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor(): CharacterDataSource {

    override suspend fun getCharacters(page: Int, nameFiltered: String?, genderFiltered: String?, statusFiltered: String?): CharacterList {
        return RickAndMortyApi.service
            .getCharactersAsync(
                page = page,
                nameFiltered = nameFiltered,
                genderFiltered = genderFiltered,
                statusFiltered = statusFiltered)
    }

    override suspend fun getCharacterById(id: Int): Character {
        return RickAndMortyApi.service
            .getCharacterByIdAsync(id = id)
    }
}