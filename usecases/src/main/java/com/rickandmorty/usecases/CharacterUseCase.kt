package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharacterById(id: Int) = characterRepository.getCharacterById(id)

    suspend fun getCharacters(
        page: Int,
        nameFiltered: String? = null,
        genderFiltered: String? = null,
        statusFiltered: String? = null
    ) = characterRepository.getCharacters(
        page = page,
        nameFiltered = nameFiltered,
        genderFiltered = genderFiltered,
        statusFiltered = statusFiltered
    )
}