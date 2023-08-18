package com.rickandmorty.ui.detail

import androidx.lifecycle.viewModelScope
import com.rickandmorty.common.ScopedViewModel
import com.rickandmorty.data.Error
import com.rickandmorty.usecases.GetCharacterById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.rickandmorty.domain.Character
import kotlinx.coroutines.launch

class DetailViewModel(
    private val characterId: Int,
    private val getCharacterById: GetCharacterById): ScopedViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun findCharacter() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            val characterResponse = getCharacterById(characterId)
            characterResponse.fold(
                { exception ->
                    _state.value = UiState(error = exception)
                }, { character ->
                    _state.value = UiState(character = character)
                })

            _state.value = UiState(loading = false)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null,
        val error: Error? = null
    )
}