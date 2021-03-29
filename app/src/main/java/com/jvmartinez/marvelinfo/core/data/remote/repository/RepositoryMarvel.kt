package com.jvmartinez.marvelinfo.core.data.remote.repository

import android.app.Application
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiMarvelRepository
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.json.JSONObject
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
            override fun onResponse(
                    call: Call<ResponseMarvel>,
                    response: Response<ResponseMarvel>
            ) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        subject.onNext(response.body())
                        subject.onComplete()
                    } else {
                        subject.onError(
                                MarvelInfoError.showError(
                                        response.message(),
                                        response.code()
                                ).error()
                        )
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

    override fun findCharacterById(characterID: Int): Observable<ResponseMarvel> {
        val subject = BehaviorSubject.create<ResponseMarvel>()
        val responseCharacter = ApiMarvelRepository.getInstance().getService().findCharacterById(
                characterID,
                1,
                app.getString(R.string.public_key),
                MarvelInfoUtils.createHash(app)
        )
        responseCharacter.enqueue(object : Callback<ResponseMarvel> {
            override fun onResponse(
                    call: Call<ResponseMarvel>,
                    response: Response<ResponseMarvel>
            ) {
                when {
                    response.isSuccessful -> {
                        subject.onNext(response.body())
                        subject.onComplete()
                    }
                    response.code() in 400..499 -> {
                        val errorBody = JSONObject(response.errorBody()!!.string())
                        subject.onError(
                                MarvelInfoError.showError(
                                        errorBody.getString("status"),
                                        errorBody.getString("code").toInt()
                                ).error()
                        )
                    }
                    else -> {
                        MarvelInfoError.showError(
                                "fail",
                                0
                        ).error()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMarvel>, t: Throwable) {
                subject.onError(t)
            }
        })
        return subject
    }

    override fun findAllComicsByCharacter(characterID: Int): Observable<ResponseMarvelComic> {
        val subject = BehaviorSubject.create<ResponseMarvelComic>()
        val responseComics = ApiMarvelRepository.getInstance().getService().findAllComicsByCharacter(
                characterID,
                1,
                app.getString(R.string.public_key),
                MarvelInfoUtils.createHash(app)
        )
        responseComics.enqueue(object : Callback<ResponseMarvelComic> {
            override fun onResponse(
                    call: Call<ResponseMarvelComic>,
                    response: Response<ResponseMarvelComic>
            ) {
                when {
                    response.isSuccessful -> {
                        subject.onNext(response.body())
                        subject.onComplete()
                    }
                    response.code() in 400..499 -> {
                        val errorBody = JSONObject(response.errorBody()!!.string())
                        subject.onError(
                                MarvelInfoError.showError(
                                        errorBody.getString("status"),
                                        errorBody.getString("code").toInt()
                                ).error()
                        )
                    }
                    else -> {
                        MarvelInfoError.showError(
                                "fail",
                                0
                        ).error()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMarvelComic>, t: Throwable) {
                subject.onError(t)
            }
        })
        return subject
    }
}