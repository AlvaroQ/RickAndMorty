package com.rickandmorty.data.database

import com.rickandmorty.data.source.LocalDataSource
import com.rickandmorty.data.toDomainCharacter
import com.rickandmorty.data.toRoomCharacter
import com.rickandmorty.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSource @Inject constructor(db: FavoriteDataBase) : LocalDataSource {
    private val favoriteDao = db.favoriteCharacterDao()

    override suspend fun isEmpty(): Boolean {
        return favoriteDao.favoriteCount() <= 0
    }

    override fun allFavoritesFlow(): Flow<List<Character>> =
        favoriteDao.favoriteListFlow().map { characterListDB ->
            characterListDB.map { it.toDomainCharacter() }
        }

    override suspend fun getAllFavoriteCharacters(): List<Character> {
        return favoriteDao.favoriteCharactersList().map { characterListDB ->
            characterListDB.toDomainCharacter()
        }
    }

    override suspend fun isFavoriteCharacterById(id: Int): Boolean {
        return favoriteDao.isFavoriteCharacters(id) != null
    }

    override suspend fun insertFavoriteCharacter(character: Character) {
        favoriteDao.insertFavoriteCharacter(character.toRoomCharacter())
    }

    override suspend fun deleteFavoriteCharacter(character: Character) {
        favoriteDao.deleteFavoriteCharacter(character.toRoomCharacter())
    }

}