package com.alvaroquintana.rickandmorty.usecases

import com.alvaroquintana.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(id: Int) = characterRepository.getCharacterById(id)
}