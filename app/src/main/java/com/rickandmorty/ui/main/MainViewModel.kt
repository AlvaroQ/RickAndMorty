package com.rickandmorty.ui.main

import androidx.lifecycle.viewModelScope
import com.rickandmorty.common.ScopedViewModel
import com.rickandmorty.domain.CharacterList
import com.rickandmorty.usecases.GetCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.rickandmorty.data.Error

class MainViewModel (private val getCharacter: GetCharacter) : ScopedViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun getCharacters() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            val characterListResponse = getCharacter()
            characterListResponse.fold(
                { exception ->
                    _state.value = UiState(error = exception)
                }, { characterList ->
                    _state.value = UiState(characterList = characterList)
            })

            _state.value = UiState(loading = false)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val characterList: CharacterList? = null,
        val error: Error? = null
    )
}