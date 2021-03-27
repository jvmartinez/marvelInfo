package com.jvmartinez.marvelinfo.core.data.remote.repository

import android.app.Application
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiMarvelRepository
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RepositoryMarvel(private val app: Application) : RepositoryContract {


    override fun findAllCharacter(): Observable<ResponseMarvel> {
        val subject = BehaviorSubject.create<ResponseMarvel>()

        val responseAllCharacter = ApiMarvelRepository.getInstance().getService().findAllCharacter(
                1,
                app.getString(R.string.public_key),
                MarvelInfoUtils.createHash(app)
        )
        responseAllCharacter.enqueue(object : Callback<ResponseMarvel> {
            override fun onResponse(call: Call<ResponseMarvel>, response: Response<ResponseMarvel>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        subject.onNext(response.body())
                        subject.onComplete()
                    } else {
                        subject.onError(MarvelInfoError.showError(response.message(), response.code()).error())
                    }
                } else {
                    subject.onError(MarvelInfoError.ERROR_GENERIC.error())
                }

            }

            override fun onFailure(call: Call<ResponseMarvel>, t: Throwable) {
                subject.onError(t)
            }

        })
        return subject
    }

    override fun findCharacterById(idCharacter: Int): Observable<ResponseMarvel> {
        val subject = BehaviorSubject.create<ResponseMarvel>()
        return subject
    }

}