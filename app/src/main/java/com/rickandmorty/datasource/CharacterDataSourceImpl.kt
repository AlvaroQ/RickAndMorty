package com.rickandmorty.datasource

import com.rickandmorty.managers.ServerApi
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList

class CharacterDataSourceImpl(private val serverApi: ServerApi): CharacterDataSource {

    override suspend fun getCharacters(page: Int, nameFiltered: String?, genderFiltered: String?, statusFiltered: String?): CharacterList {
        return serverApi.service.getCharacters(
            page = page,
            nameFiltered = nameFiltered,
            genderFiltered = genderFiltered,
            statusFiltered = statusFiltered)
    }

    override suspend fun getCharacterById(id: Int): Character {
        return serverApi.service.getCharacterById(id)
    }
}