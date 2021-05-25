package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryMarvelContract {

    @GET("v1/public/characters")
    suspend fun findAllCharacter(
        @Query("ts") ts: Int,
        @Query("apikey") key: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int? =  null,
        @Query("nameStartsWith") nameCharacter: String? = null
    ) : ResponseMarvel

    @GET("v1/public/characters/{characterId}")
    suspend fun findCharacterById(
            @Path("characterId") id: Int,
            @Query("ts") ts: Int,
            @Query("apikey") key: String,
            @Query("hash") hash: String
    ) : ResponseMarvel

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun findAllComicsByCharacter(
            @Path("characterId") id: Int,
            @Query("ts") ts: Int,
            @Query("apikey") key: String,
            @Query("hash") hash: String
    ) : ResponseMarvelComic

    @GET("v1/public/characters/{characterId}/series")
    suspend fun findAllSeriesByCharacter(
        @Path("characterId") id: Int,
        @Query("ts") ts: Int,
        @Query("apikey") key: String,
        @Query("hash") hash: String
    ) : ResponseMarvelComic

}