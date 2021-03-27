package com.jvmartinez.marvelinfo.core.data.remote.apiMarvel

class ApiMarvelRepository : ApiMarvel() {
    fun getService(): RepositoryMarvelContract {
        return getRetrofit().create(RepositoryMarvelContract::class.java)
    }

    companion object {
        fun getInstance() : ApiMarvelRepository {
            return ApiMarvelRepository()
        }
    }
}