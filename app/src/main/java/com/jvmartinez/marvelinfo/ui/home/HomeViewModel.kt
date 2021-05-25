package com.jvmartinez.marvelinfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: RepositoryMarvel) : ViewModel() {
    private var total: Int = 1

    fun findCharacters(offset: Int, nameCharacter: String? = null) = liveData(Dispatchers.IO) {
        try {
            if (nameCharacter.isNullOrEmpty()) {
                if (offset != total) {
                    emit(ApiResource.Success(repository.findAllCharacter(offset)))
                }
            } else {
                emit(ApiResource.Success(repository.findAllCharacter(offset, nameCharacter)))
            }
        } catch (exception: Exception) {
            emit(ApiResource.Failure(exception.message.toString(), exception))
        }
    }
}