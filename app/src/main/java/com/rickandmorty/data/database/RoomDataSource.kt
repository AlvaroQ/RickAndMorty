package com.rickandmorty.data.database

import com.rickandmorty.data.source.LocalDataSource
import com.rickandmorty.data.toDomainCharacter
import com.rickandmorty.data.toRoomCharacter
import com.rickandmorty.domain.Character
import javax.inject.Inject

class RoomDataSource @Inject constructor(db: FavoriteDataBase) : LocalDataSource {
    private val favoriteDao = db.favoriteCharacterDao()
    override suspend fun isEmpty(): Boolean {
        return favoriteDao.favoriteCount() <= 0
    }

    override suspend fun getAllFavoriteCharacters(): List<Character> {
        return favoriteDao.getAllFavoriteCharacters().map { it.toDomainCharacter() }
    }

    override suspend fun isFavoriteCharacters(character: Character): Boolean {
        return favoriteDao.isFavoriteCharacters(character.id) != null
    }

    override suspend fun insertFavoriteCharacter(character: Character) {
        favoriteDao.insertFavoriteCharacter(character.toRoomCharacter())
    }

    override suspend fun deleteFavoriteCharacter(character: Character) {
        favoriteDao.deleteFavoriteCharacter(character.toRoomCharacter())
    }

}