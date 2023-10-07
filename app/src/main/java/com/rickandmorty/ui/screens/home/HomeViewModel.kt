package com.rickandmorty.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.common.TAB_CHARACTERS
import com.rickandmorty.data.Error
import com.rickandmorty.domain.Character
import com.rickandmorty.usecases.AllFavoriteCharactersFlowUseCase
import com.rickandmorty.usecases.GetAllCharacterUseCase
import com.rickandmorty.usecases.UpdateFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DELAY_TO_REFRESH_LIST = 10L

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allFavoriteCharactersFlowUseCase: AllFavoriteCharactersFlowUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteCharacterUseCase,
    private val getAllCharacterUseCase: GetAllCharacterUseCase
) : ViewModel() {
    var visibleCards: Int = 0
    var nameFilter: String? = null
    var genderFilter: String? = null
    var statusFilter: String? = null
    var selectedTabIndex: Int = TAB_CHARACTERS

    private var totalPages: Int = 1
    var nextPage: Int = 1

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _favState = MutableStateFlow<List<Character>>(emptyList())
    val favState: StateFlow<List<Character>> = _favState.asStateFlow()

    init {
        viewModelScope.launch {
            requestCharacters()
            requestFavCharacters()
        }
    }

    fun reloadListWithFav() {
        val resultList = _state.value.characterList?.map { character ->
            if (_favState.value.find { it.id == character.id } != null) {
                character.copy(favorite = true)
            } else {
                character.copy(favorite = false)
            }
        }
        _state.update { it.copy(characterList = resultList) }
    }

    fun findCharacters() {
        visibleCards = 0
        viewModelScope.launch {
            if (selectedTabIndex == TAB_CHARACTERS) {
                requestCharacters()
            } else {
                requestFavCharacters()
            }
        }
    }

    private fun favoriteListFiltered(favoritesList: List<Character>): List<Character> {
        return favoritesList.filter { character ->
            (nameFilter == null || character.name.contains(
                nameFilter.toString(), ignoreCase = true
            )) && (genderFilter == null || character.gender.equals(
                genderFilter,
                ignoreCase = true
            )) && (statusFilter == null || character.status.equals(
                statusFilter,
                ignoreCase = true
            ))
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
        _state.update { it.copy(loading = true) }
        val characterListResponse = getAllCharacterUseCase(
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

            if (characterList.info.next != null) {
                val uri: Uri = Uri.parse(characterList.info.next)
                nextPage = uri.getQueryParameter(PAGE)?.toInt() ?: 0
                totalPages = characterList.info.pages
                _state.update { it.copy(noMoreItemFound = false) }
            } else {
                nextPage = totalPages
                _state.update { it.copy(noMoreItemFound = true) }
            }
        })
        delay(DELAY_TO_REFRESH_LIST)
        _state.update { it.copy(loading = false) }
    }

    fun saveFavorite(isFavorite: Boolean, character: Character) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            updateFavoriteUseCase(isFavorite, character)
            _state.update { it.copy(loading = false) }
        }
    }

    private suspend fun requestFavCharacters() {
        allFavoriteCharactersFlowUseCase().collect { favoritesList ->
            _favState.value = favoriteListFiltered(favoritesList)
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