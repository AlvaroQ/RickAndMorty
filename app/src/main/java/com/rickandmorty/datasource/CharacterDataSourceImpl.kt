package com.rickandmorty.datasource

import com.rickandmorty.managers.ServerApi
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList

class CharacterDataSourceImpl(private val serverApi: ServerApi): CharacterDataSource {

    override suspend fun getCharacters(): CharacterList {
        return serverApi.service.getCharacters()
    }

    override suspend fun getCharacterById(id: Int): Character {
        return serverApi.service.getCharacterById(id)
    }
}