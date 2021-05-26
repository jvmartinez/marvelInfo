package com.jvmartinez.marvelinfo.core.data.remote.repository

import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic

interface RepositoryContract {
    /**
     * Find all character
     */
    suspend fun findAllCharacter(offset: Int, nameCharacter: String? =  null) : ResponseMarvel

    /**
     * Find all character by id
     * @param idCharacter: Id of character
     */
    suspend fun findCharacterById(characterID: Int) : ResponseMarvel

    /**
     * Find all comics of character
     */
    suspend fun findAllComicsByCharacter(characterID: Int) : ResponseMarvelComic

    /**
     * Find all series of character
     */
    suspend fun findAllSeriesByCharacter(characterID: Int) : ResponseMarvelComic
}