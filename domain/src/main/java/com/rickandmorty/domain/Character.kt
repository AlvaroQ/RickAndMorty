package com.rickandmorty.domain

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
    val episode: ArrayList<String?>,
    val location: DataObject,
    val origin: DataObject
)