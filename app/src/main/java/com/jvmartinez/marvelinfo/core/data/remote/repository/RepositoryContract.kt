package com.jvmartinez.marvelinfo.core.data.remote.repository

import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
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
    fun findCharacterById(idCharacter: Int) : Observable<ResponseMarvel>
}