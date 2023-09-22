package com.rickandmorty.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.data.Error
import com.rickandmorty.domain.Character
import com.rickandmorty.usecases.FavoriteCharactersUseCase
import com.rickandmorty.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val favoriteCharactersUseCase: FavoriteCharactersUseCase
) : ViewModel() {
    var nameFilter: String? = null
    var genderFilter: String? = null
    var statusFilter: String? = null

    private var totalPages: Int = 1
    var nextPage: Int = 1

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        findCharacters()
    }

    fun findCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(loading = true) }
            requestCharacters()
            _state.update { it.copy(loading = false) }
        }
    }

    fun updateList() {
        if (nextPage < totalPages) {
            findCharacters()
        } else {
            _state.update { it.copy(noMoreItemFound = true) }
        }
    }

    fun cleanList() {
        _state.update { it.copy(characterList = emptyList()) }
    }

    private suspend fun requestCharacters() {
        val characterListResponse = getCharacterUseCase(
            page = nextPage,
            nameFiltered = nameFilter,
            genderFiltered = genderFilter,
            statusFiltered = statusFilter
        )

        characterListResponse.fold({ exception ->
            _state.update { it.copy(error = exception) }
        }, { characterList ->
            _state.update {
                it.copy(
                    characterList = (_state.value.characterList
                        ?: emptyList()).plus(characterList.results)
                )
            }

            characterList.info.next?.let {
                val uri: Uri = Uri.parse(characterList.info.next)
                nextPage = uri.getQueryParameter(PAGE)?.toInt() ?: 0
                totalPages = characterList.info.pages
                _state.update { it.copy(noMoreItemFound = false) }
            } ?: {
                nextPage = totalPages
                _state.update { it.copy(noMoreItemFound = true) }
            }
        })

        reloadCharacterListWithFavorites()
    }

    fun saveFavorite(isFavorite: Boolean, character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                favoriteCharactersUseCase.deleteFavoriteCharacter(character)
            } else {
                favoriteCharactersUseCase.insertFavoriteCharacter(character)
            }
            reloadCharacterListWithFavorites()
        }
    }

    fun reloadCharacterListWithFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoritesList: List<Character> = favoriteCharactersUseCase.favoriteCharacters()
            val finalList = (_state.value.characterList ?: emptyList()).map { serverCharacter ->
                if (favoritesList.find { it.id == serverCharacter.id } != null) {
                    serverCharacter.copy(favorite = true)
                } else {
                    serverCharacter.copy(favorite = false)
                }
            }

            _state.value = UiState(characterList = finalList)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val characterList: List<Character>? = null,
        val noMoreItemFound: Boolean = false,
        val error: Error? = null
    )

    companion object {
        const val PAGE = "page"
    }
}