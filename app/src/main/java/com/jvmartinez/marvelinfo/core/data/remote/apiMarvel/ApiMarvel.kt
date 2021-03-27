package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

open class ApiMarvel {
    private lateinit var retrofit: Retrofit

    private fun createClientApi(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    protected fun getRetrofit(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/")
            .client(createClientApi())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit
    }
}