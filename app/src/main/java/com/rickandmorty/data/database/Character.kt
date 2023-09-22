package com.rickandmorty.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "Favorite")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val status: String,
    val species: String,
    val type: String,
    val url: String,
    val created: String,
    @field:TypeConverters(EpisodeConverter::class) val episode: EpisodeList,
    @Embedded(prefix = "location_") val location: DataObject,
    @Embedded(prefix = "origin_") val origin: DataObject,
    val favorite: Boolean = true
)

data class EpisodeList(
    val episodeList: ArrayList<String>
)

data class DataObject(
    val name: String,
    val url: String
)

class EpisodeConverter {

    @TypeConverter
    fun fromEpisode(value: EpisodeList?): String {
        return Gson().toJson(value ?: EpisodeList(ArrayList()))
    }


    @TypeConverter
    fun toEpisode(value: String): EpisodeList {
        return Gson().fromJson(value, EpisodeList::class.java)
    }
}