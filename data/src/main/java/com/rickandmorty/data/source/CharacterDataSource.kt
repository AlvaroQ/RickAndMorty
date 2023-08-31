package com.rickandmorty.data.source

import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList

interface CharacterDataSource {

    suspend fun getCharacters(page: Int, nameFiltered: String?, genderFiltered: String?, statusFiltered: String?): CharacterList

    suspend fun getCharacterById(id: Int): Character
}