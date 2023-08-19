package com.rickandmorty.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rickandmorty.R
import com.rickandmorty.common.errorToString
import com.rickandmorty.common.hideKeyboard
import com.rickandmorty.common.startActivity
import com.rickandmorty.common.viewBinding
import com.rickandmorty.databinding.ActivityMainBinding
import com.rickandmorty.domain.Character
import com.rickandmorty.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ScopeActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val mainBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        mainBinding.toolbar.title = getString(R.string.app_name)

        adapter = CharactersAdapter(mainViewModel::onCharacterClicked)
        mainBinding.recyclerCharacter.adapter = adapter

        mainViewModel.findCharacters()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mainViewModel.state.collect(::updateUI)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = getString(R.string.query_hint)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { mainViewModel.findFilteredCharacters(query) }
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun updateUI(state: MainViewModel.UiState) {
        mainBinding.progressBar.visibility = if(state.loading) View.VISIBLE else View.GONE
        state.characterList?.let { adapter.setItems(it.results as ArrayList<Character>) }
        state.error?.let { Toast.makeText(this, errorToString(it), Toast.LENGTH_LONG).show() }
        state.navigateToDetail?.let { startActivity<DetailActivity> { putExtra(DetailActivity.CHARACTER_ID, it.id) } }
    }
}