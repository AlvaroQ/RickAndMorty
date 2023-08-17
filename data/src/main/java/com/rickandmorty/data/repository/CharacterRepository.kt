package com.rickandmorty.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.rickandmorty.data.Error
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.data.toError
import com.rickandmorty.domain.Character
import com.rickandmorty.domain.CharacterList

class CharacterRepository(private val characterDataSource: CharacterDataSource) {

    suspend fun getCharacters(): Either<Error, CharacterList> {
        return try {
            characterDataSource.getCharacters().right()
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