package com.rickandmorty.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.rickandmorty.data.Error
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.data.toError
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val characterDataSource: CharacterDataSource) {

    suspend fun getCharacters(page: Int, nameFiltered: String?, statusFiltered: String?, genderFiltered: String?): Either<Error, CharacterList> {
        return try {
            characterDataSource.getCharacters(
                page = page,
                nameFiltered = nameFiltered,
                genderFiltered = genderFiltered,
                statusFiltered = statusFiltered).right()
        } catch (e: Exception) {
            e.toError().left()
        }
    }

    suspend fun getCharacterById(id: Int): Either<Error, Character> {
        return try {
            characterDataSource.getCharacterById(id).right()
        } catch (e: Exception) {
            e.toError().left()
        }
    }
}