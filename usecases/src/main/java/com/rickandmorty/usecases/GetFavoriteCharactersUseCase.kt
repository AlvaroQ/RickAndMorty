package com.rickandmorty.usecases

import com.rickandmorty.data.repository.FavoriteRepository
import com.rickandmorty.domain.Character
import javax.inject.Inject

class FavoriteCharactersUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend fun isFavoriteCharacters(character: Character) =
        favoriteRepository.isFavoriteCharacter(character)

    suspend fun favoriteCharacters() =
        favoriteRepository.getAllFavoriteCharacters()

    suspend fun insertFavoriteCharacter(character: Character) =
        favoriteRepository.insertFavoriteCharacter(character)

    suspend fun deleteFavoriteCharacter(character: Character) =
        favoriteRepository.deleteFavoriteCharacter(character)
}