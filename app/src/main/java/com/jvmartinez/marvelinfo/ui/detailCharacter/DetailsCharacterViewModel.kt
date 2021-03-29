package com.jvmartinez.marvelinfo.ui.detailCharacter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import io.reactivex.schedulers.Schedulers


class DetailsCharacterViewModel(private val repository: RepositoryMarvel) : ViewModel() {
    private var responseLiveData: MutableLiveData<ResponseMarvel> = MutableLiveData()
    val checkResponse: LiveData<ResponseMarvel>
        get() = responseLiveData

    private var statusDetailsCharacterError: MutableLiveData<BaseEnum> = MutableLiveData()
    val checkError: LiveData<BaseEnum>
        get() = statusDetailsCharacterError

    private var responseComicLiveData: MutableLiveData<ResponseMarvelComic> = MutableLiveData()
    val checkResponseComic: LiveData<ResponseMarvelComic>
        get() = responseComicLiveData

    fun findCharacterById(characterId : Int) {
        repository.findCharacterById(characterId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(::processCharacter, ::errorProcess)
    }

    private fun processCharacter(responseMarvel: ResponseMarvel?) {
        responseLiveData.postValue(responseMarvel)
    }

    private fun errorProcess(throwable: Throwable?) {
        when (MarvelInfoError.showError(throwable?.message.toString())) {
            MarvelInfoError.ERROR_API_KEY -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_API_KEY)
            }
            MarvelInfoError.ERROR_HASH ->  {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_HASH)
            }
            MarvelInfoError.ERROR_TIMESTAMP ->  {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_TIMESTAMP)
            }
            MarvelInfoError.ERROR_REFERER ->  {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_REFERER)
            }
            MarvelInfoError.ERROR_INVALID_HASH -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_INVALID_HASH)
            }
            MarvelInfoError.ERROR_NOT_ALLOWED -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_NOT_ALLOWED)
            }
            MarvelInfoError.ERROR_FORBIDDEN -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_FORBIDDEN)
            }
            MarvelInfoError.ERROR_GENERIC -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_GENERIC)
            }
            MarvelInfoError.ERROR_PARAMETER_ID -> {
                statusDetailsCharacterError.postValue(BaseEnum.ERROR_PARAMETER)
            }
        }
    }

    fun findAllComics(characterId: Int) {
        repository.findAllComicsByCharacter(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(::processComics, ::errorProcess)
    }

    private fun processComics(responseMarvelComic: ResponseMarvelComic?) {
        responseComicLiveData.postValue(responseMarvelComic)
    }
}