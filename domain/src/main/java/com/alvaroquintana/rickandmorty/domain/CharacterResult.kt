package com.alvaroquintana.rickandmorty.domain

data class CharacterResult(
    val info: Info,
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val status: String,
    val species: String,
    val type: String,
    val url: String,
    val created: String,
    val episode: ArrayList<String>,
    val location: DataObject,
    val origin: DataObject,
    var favorite: Boolean = false
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class DataObject(
    val name: String,
    val url: String
)