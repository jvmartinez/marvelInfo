package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private fun createClientApi(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/")
            .client(createClientApi())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    val apiService: RepositoryMarvelContract = getRetrofit().create(RepositoryMarvelContract::class.java)
}