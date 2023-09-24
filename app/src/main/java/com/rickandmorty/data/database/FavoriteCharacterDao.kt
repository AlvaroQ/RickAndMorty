package com.rickandmorty.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.rickandmorty.domain.Character as CharacterDomain

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT COUNT(id) FROM Favorite")
    suspend fun favoriteCount(): Int

    @Query("SELECT * FROM Favorite")
    fun getAllFavoriteCharacters(): Flow<List<Character>>

    @Query("SELECT * FROM Favorite WHERE id = :idCharacter LIMIT 1")
    fun isFavoriteCharacters(idCharacter: Int): Character?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteCharacter(favoriteCharacter: Character)

    @Delete
    suspend fun deleteFavoriteCharacter(favoriteCharacter: Character)
}