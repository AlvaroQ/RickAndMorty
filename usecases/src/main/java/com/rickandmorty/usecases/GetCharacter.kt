package com.rickandmorty.usecases

import arrow.core.Either
import com.rickandmorty.data.Error
import com.rickandmorty.data.repository.CharacterRepository
import com.rickandmorty.domain.CharacterList

class GetCharacter(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(
        page: Int,
        nameFiltered: String? = null,
        genderFiltered: String? = null,
        statusFiltered: String? = null): Either<Error, CharacterList> {
            return characterRepository.getCharacters(
                page = page,
                nameFiltered = nameFiltered,
                genderFiltered = genderFiltered,
                statusFiltered = statusFiltered)
    }
}