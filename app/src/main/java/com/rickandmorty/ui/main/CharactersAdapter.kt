package com.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.common.basicDiffUtil
import com.rickandmorty.common.glideLoadURL
import com.rickandmorty.databinding.ItemCharacterBinding
import com.rickandmorty.domain.Character

class CharactersAdapter(private val listener: (Character) -> Unit) : RecyclerView.Adapter<CharacterViewHolder>() {
    var items: MutableList<Character> by basicDiffUtil(
        mutableListOf(),
        areItemsTheSame = { old, new -> old.id == new.id })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)
        holder.itemView.setOnClickListener { listener(character) }
    }
}

class CharacterViewHolder(private val itemBinding: ItemCharacterBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var character: Character

    fun bind(item: Character) {
        this.character = item
        itemBinding.nameCharacter.text = item.name
        glideLoadURL(item.image, itemBinding.imageCharacter)
    }
}

