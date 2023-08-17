package com.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rickandmorty.R
import com.rickandmorty.common.viewBinding
import com.rickandmorty.databinding.ActivityMainBinding
import com.rickandmorty.domain.Character
import kotlinx.coroutines.launch
import com.rickandmorty.data.Error

class MainActivity : ScopeActivity(), CharactersAdapter.CharacterItemListener {
    private val mainViewModel: MainViewModel by viewModel()
    private val mainBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)


        adapter = CharactersAdapter(this)
        mainBinding.recyclerCharacter.adapter = adapter

        mainViewModel.getCharacters()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect(::updateUI)
            }
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        mainBinding.progressBar.visibility = if(state.loading) View.VISIBLE else View.GONE
        state.characterList?.let { adapter.setItems(state.characterList.results as ArrayList<Character>) }

        if(state.error != null) {
            Toast.makeText(this, errorToString(state.error), Toast.LENGTH_LONG).show()
        }
    }

    private fun errorToString(error: Error) = when(error) {
        Error.Connectivity -> getString(R.string.error_connectivity)
        is Error.Server -> getString(R.string.error_server) + error.code
        is Error.Unknown -> getString(R.string.error_unknown) + error.message
    }

    override fun onClickedCharacter(characterId: Int) {
        Log.d("onClickedCharacter", "characterId = $characterId")
    }
}