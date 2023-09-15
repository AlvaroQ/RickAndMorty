package com.rickandmorty.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.data.Error
import com.rickandmorty.domain.Character
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
class HomeViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase) :
    ViewModel() {
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
        _state.value.characterList = emptyList()
    }

    private suspend fun requestCharacters() {
        val characterListResponse = getCharacterUseCase(
            page = nextPage,
            nameFiltered = nameFilter,
            genderFiltered = genderFilter,
            statusFiltered = statusFilter
        )

        characterListResponse.fold(
            { exception ->
                _state.update { it.copy(error = exception) }
            }, { characterList ->
                _state.update {
                    it.copy(
                        characterList = (_state.value.characterList ?: emptyList()).plus(
                            characterList.results
                        )
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
    }

    data class UiState(
        val loading: Boolean = false,
        var characterList: List<Character>? = null,
        val noMoreItemFound: Boolean = false,
        val error: Error? = null
    )

    companion object {
        const val PAGE = "page"
    }
}