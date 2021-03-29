package com.jvmartinez.marvelinfo.core.data.remote.repository

import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import io.reactivex.Observable

interface RepositoryContract {
    /**
     * Find all character
     */
    fun findAllCharacter() : Observable<ResponseMarvel>

    /**
     * Find all character by id
     * @param idCharacter: Id of character
     */
    fun findCharacterById(characterID: Int) : Observable<ResponseMarvel>

    /**
     * Find all comics of character
     */
    fun findAllComicsByCharacter(characterID: Int) : Observable<ResponseMarvelComic>
}