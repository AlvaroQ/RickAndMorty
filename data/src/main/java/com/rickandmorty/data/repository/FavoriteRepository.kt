package com.rickandmorty.data.repository

import com.rickandmorty.data.source.LocalDataSource
import com.rickandmorty.domain.Character
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val localDataSource: LocalDataSource) {

    suspend fun getAllFavoriteCharacters(): List<Character> {
        return localDataSource.getAllFavoriteCharacters()
    }

    suspend fun isFavoriteCharacter(character: Character): Boolean {
        return localDataSource.isFavoriteCharacters(character)
    }

    suspend fun insertFavoriteCharacter(id: Character) {
        localDataSource.insertFavoriteCharacter(id)
    }

    suspend fun deleteFavoriteCharacter(id: Character) {
        localDataSource.deleteFavoriteCharacter(id)
    }
}