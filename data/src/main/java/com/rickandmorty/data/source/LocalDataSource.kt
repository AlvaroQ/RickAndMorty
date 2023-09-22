package com.rickandmorty.data.source

import com.rickandmorty.domain.Character

interface LocalDataSource {
    suspend fun isEmpty(): Boolean

    suspend fun getAllFavoriteCharacters(): List<Character>

    suspend fun isFavoriteCharacters(character: Character): Boolean

    suspend fun insertFavoriteCharacter(character: Character)

    suspend fun deleteFavoriteCharacter(character: Character)
}