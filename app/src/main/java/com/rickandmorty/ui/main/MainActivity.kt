package com.rickandmorty.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.R
import com.rickandmorty.common.errorToString
import com.rickandmorty.common.hideKeyboard
import com.rickandmorty.common.selected
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
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        mainBinding.toolbar.title = getString(R.string.app_name)

        adapter = CharactersAdapter(mainViewModel::onCharacterClicked)
        mainBinding.recyclerCharacter.adapter = adapter
        setRecyclerViewScrollListener()
        setUpFilters()

        mainViewModel.findCharacters()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mainViewModel.state.collect(::updateUI)
            }
        }
    }

    private fun setUpFilters() {
        val statusList = resources.getStringArray(R.array.status_list)
        mainBinding.spinnerStatus.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, statusList)
        mainBinding.spinnerStatus.isSelected = false
        mainBinding.spinnerStatus.setSelection(0,true)
        mainBinding.spinnerStatus.selected {
            val spinnerItemClicked = if(it == 0) null else statusList[it]

            adapter.items = mutableListOf()
            mainViewModel.nextPage = 1
            mainViewModel.statusFilter = spinnerItemClicked
            mainViewModel.findCharacters()
        }

        val genderList = resources.getStringArray(R.array.gender_list)
        mainBinding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderList)
        mainBinding.spinnerGender.isSelected = false
        mainBinding.spinnerGender.setSelection(0,true)
        mainBinding.spinnerGender.selected {
            val spinnerItemClicked = if(it == 0) null else genderList[it]

            adapter.items = mutableListOf()
            mainViewModel.nextPage = 1
            mainViewModel.genderFilter = spinnerItemClicked
            mainViewModel.findCharacters()
        }
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    mainViewModel.updateList()
                }
            }
        }
        mainBinding.recyclerCharacter.addOnScrollListener(scrollListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = getString(R.string.query_hint)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnCloseListener {
            adapter.items = mutableListOf()
            mainViewModel.nextPage = 1
            mainViewModel.nameFilter = null
            mainViewModel.findCharacters()
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                query?.let {
                    adapter.items = mutableListOf()
                    mainViewModel.nextPage = 1
                    mainViewModel.nameFilter = query
                    mainViewModel.findCharacters()
                }
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
        state.characterList?.let {
            val oldSize = adapter.items.size
            adapter.items.addAll(it.results as MutableList<Character>)
            adapter.notifyItemRangeInserted(oldSize, adapter.items.size)
        }
        if(state.noMoreItemFound) Toast.makeText(this, getString(R.string.no_more_item_found), Toast.LENGTH_LONG).show()
        state.error?.let { Toast.makeText(this, errorToString(it), Toast.LENGTH_LONG).show() }
        state.navigateToDetail?.let { startActivity<DetailActivity> { putExtra(DetailActivity.CHARACTER_ID, it.id) } }
    }
}