package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository

class GetCharacter(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke() = characterRepository.getCharacters()
}