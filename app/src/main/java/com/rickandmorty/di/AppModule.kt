package com.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.rickandmorty.data.database.FavoriteCharacterDao
import com.rickandmorty.data.database.FavoriteDataBase
import com.rickandmorty.data.database.RoomDataSource
import com.rickandmorty.data.server.RickAndMortyDataSource
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.data.source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataBase(app: Application): FavoriteDataBase {
        return Room.databaseBuilder(
            app,
            FavoriteDataBase::class.java,
            "favorite-characters-db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFavoriteCharacterDao(dataBase: FavoriteDataBase): FavoriteCharacterDao {
        return dataBase.favoriteCharacterDao()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RickAndMortyDataSource): CharacterDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: RoomDataSource): LocalDataSource
}