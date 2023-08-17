package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository

class GetCharacter(private val moviesRepository: CharacterRepository) {
    suspend operator fun invoke() = moviesRepository.getCharacters()
}