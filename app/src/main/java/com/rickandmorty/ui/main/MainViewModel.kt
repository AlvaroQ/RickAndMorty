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
import com.rickandmorty.domain.Character

class MainViewModel (private val getCharacter: GetCharacter) : ScopedViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun findCharacters() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            requestCharacters()
            _state.value = UiState(loading = false)
        }
    }

    private suspend fun requestCharacters() {
        val characterListResponse = getCharacter()
        characterListResponse.fold(
            { exception ->
                _state.value = UiState(error = exception)
            }, { characterList ->
                _state.value = UiState(characterList = characterList)
            })
    }

    fun onCharacterClicked(character: Character) {
        _state.value = UiState(navigateToDetail = character)
    }

    data class UiState(
        val loading: Boolean = false,
        val characterList: CharacterList? = null,
        val error: Error? = null,
        val navigateToDetail: Character? = null
    )
}