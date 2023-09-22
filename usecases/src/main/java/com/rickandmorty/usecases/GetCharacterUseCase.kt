package com.rickandmorty.usecases

import com.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(
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