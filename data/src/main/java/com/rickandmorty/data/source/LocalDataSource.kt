package com.rickandmorty.data.source

import com.rickandmorty.domain.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun isEmpty(): Boolean

    fun getAllFavoriteCharacters(): Flow<List<Character>>

    suspend fun isFavoriteCharacters(character: Character): Boolean

    suspend fun insertFavoriteCharacter(character: Character)

    suspend fun deleteFavoriteCharacter(character: Character)
}