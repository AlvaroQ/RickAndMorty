package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class AllFavoriteCharactersFlowUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    operator fun invoke() = characterRepository.allFavoriteCharactersFlow()
}