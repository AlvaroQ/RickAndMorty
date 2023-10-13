package com.alvaroquintana.rickandmorty.usecases

import com.alvaroquintana.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetAllFavoriteCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke() = characterRepository.getAllFavoriteCharacters()
}