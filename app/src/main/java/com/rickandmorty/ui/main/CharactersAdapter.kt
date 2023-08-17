package com.rickandmorty.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.rickandmorty.databinding.ItemCharacterBinding
import com.rickandmorty.domain.Character

class CharactersAdapter(private val listener: CharacterItemListener) : RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private val items = ArrayList<Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])
}

class CharacterViewHolder(private val itemBinding: ItemCharacterBinding, private val listener: CharactersAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: Character

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Character) {
        this.character = item
        itemBinding.nameCharacter.text = item.name
        itemBinding.descriptionCharacter.text = """${item.species} - ${item.status}"""
        Glide.with(itemBinding.root)
            .load(item.image)
            .transform(CircleCrop())
            .into(itemBinding.imageCharacter)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id)
    }
}

