package com.jvmartinez.marvelinfo.ui.detailCharacter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import kotlinx.coroutines.Dispatchers


class DetailsCharacterViewModel(private val repository: RepositoryMarvel) : ViewModel() {

    fun findCharacterById(characterId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(ApiResource.Success(repository.findCharacterById(characterId)))
        } catch (exception: Exception) {
            emit(ApiResource.Failure(exception.message.toString(), exception))
        }
    }


    fun findAllComics(characterId: Int, typeAction: Int) = liveData(Dispatchers.IO) {
        try {
            if (typeAction == 0) {
                emit(ApiResource.Success(repository.findAllComicsByCharacter(characterId)))
            } else {
                emit(ApiResource.Success(repository.findAllSeriesByCharacter(characterId)))
            }
        } catch (exception: Exception) {
            emit(ApiResource.Failure(exception.message.toString(), exception))
        }
    }
}