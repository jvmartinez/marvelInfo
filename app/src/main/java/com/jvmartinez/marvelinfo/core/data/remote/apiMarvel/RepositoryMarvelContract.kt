package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryMarvelContract {

    @GET("v1/public/characters")
    fun findAllCharacter(
        @Query("ts") ts: Int,
        @Query("apikey") key: String,
        @Query("hash") hash: String
    ): Call<ResponseMarvel>

    @GET("v1/public/characters/")
    fun findCharacterById(
        @Query("characterId") id: String,
        @Query("ts") ts: Int,
        @Query("apikey") key: String,
        @Query("hash") hash: String
    ): Call<ResponseMarvel>
}