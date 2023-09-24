package com.rickandmorty.data.repository

import com.rickandmorty.data.source.LocalDataSource
import com.rickandmorty.domain.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val localDataSource: LocalDataSource) {

    fun getAllFavoriteCharacters() =
        localDataSource.getAllFavoriteCharacters()

    suspend fun isFavoriteCharacter(character: Character) =
        localDataSource.isFavoriteCharacters(character)

    suspend fun insertFavoriteCharacter(id: Character) =
        localDataSource.insertFavoriteCharacter(id)

    suspend fun deleteFavoriteCharacter(id: Character) =
        localDataSource.deleteFavoriteCharacter(id)
}