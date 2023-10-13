package com.alvaroquintana.rickandmorty.usecases

import com.alvaroquintana.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
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