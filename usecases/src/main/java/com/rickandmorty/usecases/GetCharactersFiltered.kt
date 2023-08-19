package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository

class GetCharactersFiltered(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(nameFiltered: String) = characterRepository.getCharactersFiltered(nameFiltered)
}