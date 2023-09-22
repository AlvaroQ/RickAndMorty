package com.rickandmorty.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT COUNT(id) FROM Favorite")
    fun favoriteCount(): Int

    @Query("SELECT * FROM Favorite")
    fun getAllFavoriteCharacters(): List<Character>

    @Query("SELECT * FROM Favorite WHERE id = :idCharacter LIMIT 1")
    fun isFavoriteCharacters(idCharacter: Int): Character?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteCharacter(favoriteCharacter: Character)

    @Delete
    fun deleteFavoriteCharacter(favoriteCharacter: Character)
}