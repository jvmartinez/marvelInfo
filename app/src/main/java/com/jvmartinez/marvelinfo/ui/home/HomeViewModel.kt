package com.jvmartinez.marvelinfo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: RepositoryMarvel) : ViewModel() {
    private var responseLiveData: MutableLiveData<ResponseMarvel> = MutableLiveData()
    val checkResponse: LiveData<ResponseMarvel>
        get() = responseLiveData

    private var statusHomeError: MutableLiveData<HomeEnum> = MutableLiveData()
    val checkError: LiveData<HomeEnum>
        get() = statusHomeError

    private var total: Int = 1

    fun findCharacters(offset: Int) {
        if (offset != this.total) {
            repository.findAllCharacter(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(::processFindCharacters, ::errorProcessFindCharacters)
        }
    }

    private fun errorProcessFindCharacters(throwable: Throwable?) {
        when (MarvelInfoError.valueOf(throwable?.message.toString())) {
            MarvelInfoError.ERROR_API_KEY -> {
                statusHomeError.postValue(HomeEnum.ERROR_API_KEY)
            }
            MarvelInfoError.ERROR_HASH ->  {
                statusHomeError.postValue(HomeEnum.ERROR_HASH)
            }
            MarvelInfoError.ERROR_TIMESTAMP ->  {
                statusHomeError.postValue(HomeEnum.ERROR_TIMESTAMP)
            }
            MarvelInfoError.ERROR_REFERER ->  {
                statusHomeError.postValue(HomeEnum.ERROR_REFERER)
            }
            MarvelInfoError.ERROR_INVALID_HASH -> {
                statusHomeError.postValue(HomeEnum.ERROR_INVALID_HASH)
            }
            MarvelInfoError.ERROR_NOT_ALLOWED -> {
                statusHomeError.postValue(HomeEnum.ERROR_NOT_ALLOWED)
            }
            MarvelInfoError.ERROR_FORBIDDEN -> {
                statusHomeError.postValue(HomeEnum.ERROR_FORBIDDEN)
            }
            MarvelInfoError.ERROR_GENERIC -> {
                statusHomeError.postValue(HomeEnum.ERROR_GENERIC)
            } else -> {
                statusHomeError.postValue(HomeEnum.ERROR_GENERIC)
            }
        }
    }

    private fun processFindCharacters(responseMarvel: ResponseMarvel?) {
         responseMarvel?.data?.total.let {
             if (it != null) {
                 total = it
             }
         }
        responseLiveData.postValue(responseMarvel)
    }
}