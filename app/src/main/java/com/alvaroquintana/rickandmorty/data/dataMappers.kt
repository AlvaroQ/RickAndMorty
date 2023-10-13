package com.alvaroquintana.rickandmorty.data


import com.alvaroquintana.rickandmorty.data.database.EpisodeList
import com.alvaroquintana.rickandmorty.domain.Character
import com.alvaroquintana.rickandmorty.domain.CharacterResult
import com.alvaroquintana.rickandmorty.domain.DataObject
import com.alvaroquintana.rickandmorty.domain.Info
import com.alvaroquintana.rickandmorty.data.database.Character as DBCharacter
import com.alvaroquintana.rickandmorty.data.database.DataObject as DBDataObject
import com.alvaroquintana.rickandmorty.data.server.Character as ServerCharacter
import com.alvaroquintana.rickandmorty.data.server.TheCharacterResult as ServerCharacterResult

fun Character.toRoomCharacter(): DBCharacter =
    DBCharacter(
        id,
        name,
        image,
        gender,
        status,
        species,
        type,
        url,
        created,
        EpisodeList(episode),
        DBDataObject(location.name, location.url),
        DBDataObject(origin.name, origin.url),
        favorite
    )

fun DBCharacter.toDomainCharacter(): Character =
    Character(
        id,
        name,
        image,
        gender,
        status,
        species,
        type,
        url,
        created,
        episode.episodeList,
        DataObject(location.name, location.url),
        DataObject(origin.name, origin.url),
        favorite
    )

fun ServerCharacterResult.toDomainCharacter(): CharacterResult =
    CharacterResult(
        Info(info.count, info.pages, info.next, info.prev),
        results.map {
            it.toDomainCharacter()
        }

    )

fun ServerCharacter.toDomainCharacter() =
    Character(
        id,
        name,
        image,
        gender,
        status,
        species,
        type,
        url,
        created,
        episode,
        DataObject(location.name, location.url),
        DataObject(origin.name, origin.url),
        false
    )