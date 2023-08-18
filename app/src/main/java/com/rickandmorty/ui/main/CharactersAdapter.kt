package com.rickandmorty.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.rickandmorty.common.glideLoadURL
import com.rickandmorty.databinding.ItemCharacterBinding
import com.rickandmorty.domain.Character

class CharactersAdapter(private val listener: (Character) -> Unit) : RecyclerView.Adapter<CharacterViewHolder>() {
    private val items = ArrayList<Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

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

