package com.rickandmorty.ui.main

import android.net.Uri
import com.rickandmorty.domain.CharacterList
import com.rickandmorty.usecases.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.rickandmorty.data.Error
import com.rickandmorty.domain.Character
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase) : ViewModel() {
    var nameFilter: String? = null
    var genderFilter: String? = null
    var statusFilter: String? = null

    private var totalPages: Int = 1
    var nextPage: Int = 1

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun findCharacters() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            requestCharacters()
            _state.value = UiState(loading = false)
        }
    }

    fun updateList() {
        if(nextPage < totalPages) {
            findCharacters()
        } else {
            _state.value = UiState(noMoreItemFound = true)
        }
    }

    private suspend fun requestCharacters() {
        val characterListResponse = getCharacterUseCase(
            page = nextPage,
            nameFiltered = nameFilter,
            genderFiltered = genderFilter,
            statusFiltered = statusFilter)

        characterListResponse.fold(
            { exception ->
                _state.value = UiState(error = exception)
            }, { characterList ->
                _state.value = UiState(characterList = characterList)
                if(characterList.info.next != null) {
                    val uri: Uri = Uri.parse(characterList.info.next)
                    nextPage = uri.getQueryParameter(PAGE)?.toInt() ?: 0
                    totalPages = characterList.info.pages
                } else {
                    nextPage = totalPages
                    _state.value = UiState(noMoreItemFound = true)
                }
            })
    }

    fun onCharacterClicked(character: Character) {
        _state.value = UiState(navigateToDetail = character)
    }

    data class UiState(
        val loading: Boolean = false,
        val characterList: CharacterList? = null,
        val noMoreItemFound: Boolean = false,
        val error: Error? = null,
        val navigateToDetail: Character? = null
    )

    companion object {
        const val PAGE = "page"
    }
}