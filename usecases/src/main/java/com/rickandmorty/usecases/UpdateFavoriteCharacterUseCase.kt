package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository
import com.rickandmorty.domain.Character
import javax.inject.Inject

class UpdateFavoriteCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(isFavorite: Boolean, character: Character) {
        if (isFavorite) characterRepository.insertFavoriteCharacter(character.copy(favorite = true))
        else characterRepository.deleteFavoriteCharacter(character)
    }
}