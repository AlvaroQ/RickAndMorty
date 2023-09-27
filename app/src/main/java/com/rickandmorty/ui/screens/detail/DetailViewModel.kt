package com.rickandmorty.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.data.Error
import com.rickandmorty.domain.Character
import com.rickandmorty.usecases.CharacterUseCase
import com.rickandmorty.usecases.FavoriteCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val favoriteCharactersUseCase: FavoriteCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _isFav = MutableStateFlow(false)
    val isFav: StateFlow<Boolean> = _isFav.asStateFlow()

    fun findCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            val characterResponse = characterUseCase.getCharacterById(characterId)
            characterResponse.fold(
                { exception ->
                    _state.update { it.copy(error = exception) }
                }, { character ->
                    _state.update { UiState(character = character) }
                    isFavorite(character)
                })

            _state.update { it.copy(loading = false) }
        }
    }

    fun isFavorite(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favoriteCharactersUseCase.isFavoriteCharacters(character)
            _isFav.update { isFavorite }
        }
    }

    fun saveFavorite(isFavorite: Boolean, character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                favoriteCharactersUseCase.deleteFavoriteCharacter(character)
            } else {
                favoriteCharactersUseCase.insertFavoriteCharacter(character.copy(favorite = true))
            }
            isFavorite(character)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null,
        val error: Error? = null
    )
}