package com.rickandmorty.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rickandmorty.R
import com.rickandmorty.common.errorToString
import com.rickandmorty.common.glideLoadURL
import com.rickandmorty.common.viewBinding
import com.rickandmorty.databinding.ActivityDetailBinding
import com.rickandmorty.domain.Character
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val detailBinding by viewBinding(ActivityDetailBinding::inflate)

    companion object {
        const val CHARACTER_ID = "DetailActivity:character"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        setSupportActionBar(detailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val characterId = intent.extras?.getInt(CHARACTER_ID) ?: 0
        detailViewModel.findCharacter(characterId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.state.collect(::updateUI)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(state: DetailViewModel.UiState) {
        detailBinding.progressBar.visibility = if(state.loading) View.VISIBLE else View.GONE
        state.character?.let {character ->
            detailBinding.toolbar.title = character.name
            glideLoadURL(character.image, detailBinding.imageCharacter)
            updateDetail(character)
        }

        if(state.error != null) {
            Toast.makeText(this, errorToString(state.error), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateDetail(character: Character) {
        if(character.gender.isNotEmpty()) {
            detailBinding.genderTitleCharacter.visibility = View.VISIBLE
            detailBinding.genderTitleCharacter.text = getString(R.string.gender, character.gender)
        }

        if(character.status.isNotEmpty()) {
            detailBinding.statusTitleCharacter.visibility = View.VISIBLE
            detailBinding.statusTitleCharacter.text = getString(R.string.status, character.status)
        }

        if(character.species.isNotEmpty()) {
            detailBinding.speciesTitleCharacter.visibility = View.VISIBLE
            detailBinding.speciesTitleCharacter.text = getString(R.string.species, character.species)
        }

        if(character.type.isNotEmpty()) {
            detailBinding.typeTitleCharacter.visibility = View.VISIBLE
            detailBinding.typeTitleCharacter.text = getString(R.string.type, character.type)
        }
    }
}