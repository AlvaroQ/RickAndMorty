package com.alvaroquintana.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.alvaroquintana.rickandmorty.data.database.FavoriteCharacterDao
import com.alvaroquintana.rickandmorty.data.database.FavoriteDataBase
import com.alvaroquintana.rickandmorty.data.database.RoomDataSource
import com.alvaroquintana.rickandmorty.data.server.RickAndMortyDataSource
import com.alvaroquintana.rickandmorty.data.source.CharacterDataSource
import com.alvaroquintana.rickandmorty.data.source.LocalDataSource
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