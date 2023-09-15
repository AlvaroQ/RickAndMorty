package com.rickandmorty.di

import com.rickandmorty.data.server.RickAndMortyDataSource
import com.rickandmorty.data.source.CharacterDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//class AppModule {
//    @Provides
//    @Singleton
//    fun remoteDataSourceProvider(): CharacterDataSource = RickAndMortyDataSource()
//}


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AppDataModule {
//
//    fun CharacterRepositoryProvider(
//        characterDataSource: CharacterDataSource
//    ) = CharacterRepository(characterDataSource)
//}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RickAndMortyDataSource): CharacterDataSource
}