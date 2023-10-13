package com.alvaroquintana.rickandmorty.data.source

import com.alvaroquintana.rickandmorty.domain.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun isEmpty(): Boolean

    fun allFavoritesFlow(): Flow<List<Character>>

    suspend fun getAllFavoriteCharacters(): List<Character>

    suspend fun isFavoriteCharacterById(id: Int): Boolean

    suspend fun insertFavoriteCharacter(character: Character)

    suspend fun deleteFavoriteCharacter(character: Character)
}