package com.rickandmorty

import android.app.Application
import com.rickandmorty.managers.ServerApi
import com.rickandmorty.data.repository.CharacterRepository
import com.rickandmorty.data.source.CharacterDataSource
import com.rickandmorty.datasource.CharacterDataSourceImpl
import com.rickandmorty.ui.main.MainActivity
import com.rickandmorty.ui.main.MainViewModel
import com.rickandmorty.usecases.GetCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(appModule, dataModule, scopesModule)
    }
}

private val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<CharacterDataSource> { CharacterDataSourceImpl(get()) }
    single { ServerApi() }
}

val dataModule = module {
    factory { CharacterRepository(get()) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetCharacter(get()) }
    }
}