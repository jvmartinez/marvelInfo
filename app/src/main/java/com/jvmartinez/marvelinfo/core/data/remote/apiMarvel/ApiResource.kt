package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

sealed class ApiResource<out T> {
    data class Success<out T>(val data: T) : ApiResource<T>()
    data class Failure(val message: String, val exception: Throwable? = null) :
        ApiResource<Nothing>()
}
