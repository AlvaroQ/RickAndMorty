package com.alvaroquintana.rickandmorty.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.alvaroquintana.rickandmorty.data.Error
import com.alvaroquintana.rickandmorty.data.source.CharacterDataSource
import com.alvaroquintana.rickandmorty.data.source.LocalDataSource
import com.alvaroquintana.rickandmorty.data.toError
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.domain.CharacterResult
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDataSource: CharacterDataSource,
    private val localDataSource: LocalDataSource
) {

    fun allFavoriteCharactersFlow() =
        localDataSource.allFavoritesFlow()

    suspend fun getAllFavoriteCharacters() =
        localDataSource.getAllFavoriteCharacters()

    suspend fun insertFavoriteCharacter(id: Character) =
        localDataSource.insertFavoriteCharacter(id)

    suspend fun deleteFavoriteCharacter(id: Character) =
        localDataSource.deleteFavoriteCharacter(id)

    suspend fun getCharacters(
        page: Int,
        nameFiltered: String?,
        statusFiltered: String?,
        genderFiltered: String?
    ): Either<Error, CharacterResult> {
        return try {
            val favoritesList = localDataSource.getAllFavoriteCharacters()
            val characterList = characterDataSource.getCharacters(
                page = page,
                nameFiltered = nameFiltered,
                genderFiltered = genderFiltered,
                statusFiltered = statusFiltered
            )

            val charactersListFinal = characterList.results.mapWithFavoriteList(favoritesList)
            CharacterResult(characterList.info, charactersListFinal).right()
        } catch (e: Exception) {
            e.toError().left()
        }
    }

    suspend fun getCharacterById(id: Int): Either<Error, Character> {
        return try {
            val character = characterDataSource.getCharacterById(id)
            val isFavorite = localDataSource.isFavoriteCharacterById(id)

            character.copy(favorite = isFavorite).right()
        } catch (e: Exception) {
            e.toError().left()
        }
    }

    private fun (List<Character>).mapWithFavoriteList(favoritesList: List<Character>): List<Character> {
        val resultList = map { serverCharacter ->
            if (favoritesList.find { it.id == serverCharacter.id } != null) {
                serverCharacter.copy(favorite = true)
            } else {
                serverCharacter.copy(favorite = false)
            }
        }
        return resultList
    }
}