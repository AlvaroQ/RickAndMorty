package com.alvaroquintana.rickandmorty.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaroquintana.rickandmorty.data.Error
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.usecases.GetCharacterByIdUseCase
import com.alvaroquintana.rickandmorty.usecases.UpdateFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteCharacterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun findCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            getCharacterByIdUseCase(characterId).fold({ exception ->
                _state.update { it.copy(error = exception) }
            }, { character ->
                _state.update { UiState(character = character) }
            })

            _state.update { it.copy(loading = false) }
        }
    }

    fun saveFavorite(isFavorite: Boolean, character: Character) {
        viewModelScope.launch {
            updateFavoriteUseCase(isFavorite, character)
            _state.update { UiState(character = character.copy(favorite = isFavorite)) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null,
        val error: Error? = null
    )
}