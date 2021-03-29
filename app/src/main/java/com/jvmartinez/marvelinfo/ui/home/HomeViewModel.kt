package com.jvmartinez.marvelinfo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: RepositoryMarvel) : ViewModel() {
    private var responseLiveData: MutableLiveData<ResponseMarvel> = MutableLiveData()
    val checkResponse: LiveData<ResponseMarvel>
        get() = responseLiveData

    private var statusHomeError: MutableLiveData<BaseEnum> = MutableLiveData()
    val checkError: LiveData<BaseEnum>
        get() = statusHomeError

    private var responseLiveDataByName: MutableLiveData<ResponseMarvel> = MutableLiveData()
    val checkResponseByName: LiveData<ResponseMarvel>
        get() = responseLiveDataByName
    private var total: Int = 1

    fun findCharacters(offset: Int, nameCharacter: String? = null) {
        if (nameCharacter.isNullOrEmpty()) {
            if (offset != this.total) {
                repository.findAllCharacter(offset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(::processFindCharacters, ::errorProcessFindCharacters)
            }
        } else {
            repository.findAllCharacter(offset, nameCharacter)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(::processFindCharactersByName, ::errorProcessFindCharacters)
        }
    }

    private fun errorProcessFindCharacters(throwable: Throwable?) {
        when (MarvelInfoError.showError(throwable?.message.toString())) {
            MarvelInfoError.ERROR_API_KEY -> {
                statusHomeError.postValue(BaseEnum.ERROR_API_KEY)
            }
            MarvelInfoError.ERROR_HASH ->  {
                statusHomeError.postValue(BaseEnum.ERROR_HASH)
            }
            MarvelInfoError.ERROR_TIMESTAMP ->  {
                statusHomeError.postValue(BaseEnum.ERROR_TIMESTAMP)
            }
            MarvelInfoError.ERROR_REFERER ->  {
                statusHomeError.postValue(BaseEnum.ERROR_REFERER)
            }
            MarvelInfoError.ERROR_INVALID_HASH -> {
                statusHomeError.postValue(BaseEnum.ERROR_INVALID_HASH)
            }
            MarvelInfoError.ERROR_NOT_ALLOWED -> {
                statusHomeError.postValue(BaseEnum.ERROR_NOT_ALLOWED)
            }
            MarvelInfoError.ERROR_FORBIDDEN -> {
                statusHomeError.postValue(BaseEnum.ERROR_FORBIDDEN)
            }
            MarvelInfoError.ERROR_GENERIC -> {
                statusHomeError.postValue(BaseEnum.ERROR_GENERIC)
            } else -> {
                statusHomeError.postValue(BaseEnum.ERROR_GENERIC)
            }
        }
    }

    private fun processFindCharacters(responseMarvel: ResponseMarvel?) {
         responseMarvel?.data?.total.let {
             if (it != null) {
                 total = it
             }
         }
        responseMarvel?.data.let { characters ->
            if (characters?.results?.isEmpty() == true) {
                statusHomeError.postValue(BaseEnum.EMPTY_DATA)
            } else {
                responseLiveData.postValue(responseMarvel)
            }
        }
    }

    private fun processFindCharactersByName(responseMarvel: ResponseMarvel?) {
        responseMarvel?.data.let { characters ->
            if (characters?.results?.isEmpty() == true) {
                statusHomeError.postValue(BaseEnum.EMPTY_DATA)
            } else {
                responseLiveDataByName.postValue(responseMarvel)
            }
        }
    }
}