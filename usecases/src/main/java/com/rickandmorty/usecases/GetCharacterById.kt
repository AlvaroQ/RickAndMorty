package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository

class GetCharacterById(private val characterRepository: CharacterRepository) {
    suspend fun invoke(id: Int) = characterRepository.getCharacterById(id)
}