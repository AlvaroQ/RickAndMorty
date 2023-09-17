package com.rickandmorty.di

import com.rickandmorty.data.server.RickAndMortyDataSource
import com.rickandmorty.data.source.CharacterDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RickAndMortyDataSource): CharacterDataSource
}