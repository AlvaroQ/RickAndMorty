package com.rickandmorty.di

import com.rickandmorty.data.repository.CharacterRepository
import com.rickandmorty.data.server.RickAndMortyDataSource
import com.rickandmorty.data.source.CharacterDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun remoteDataSourceProvider(): CharacterDataSource = RickAndMortyDataSource()
}


@Module
@InstallIn(SingletonComponent::class)
class AppDataModule {
    @Provides
    fun CharacterRepositoryProvider(
        characterDataSource: CharacterDataSource
    ) = CharacterRepository(characterDataSource)
}