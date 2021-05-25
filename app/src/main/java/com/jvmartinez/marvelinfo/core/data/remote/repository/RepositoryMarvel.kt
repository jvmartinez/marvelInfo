package com.jvmartinez.marvelinfo.core.data.remote.repository

import android.app.Application
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.RepositoryMarvelContract
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.RetrofitBuilder
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils


class RepositoryMarvel(
    private val app: Application
) : RepositoryContract {
    private val repositoryMarvelContract: RepositoryMarvelContract = RetrofitBuilder.apiService

    override suspend fun findAllCharacter(offset: Int, nameCharacter: String?): ResponseMarvel {
        return if (nameCharacter?.isNotEmpty() == true) {
            repositoryMarvelContract.findAllCharacter(
                1,
                app.getString(R.string.public_key),
                MarvelInfoUtils.createHash(app),
                null,
                nameCharacter,
            )
        } else {
            repositoryMarvelContract.findAllCharacter(
                1,
                app.getString(R.string.public_key),
                MarvelInfoUtils.createHash(app),
                offset,
            )
        }
    }

    override suspend fun findCharacterById(characterID: Int): ResponseMarvel {
        return repositoryMarvelContract.findCharacterById(
            characterID,
            1,
            app.getString(R.string.public_key),
            MarvelInfoUtils.createHash(app)
        )
    }

    override suspend fun findAllComicsByCharacter(characterID: Int): ResponseMarvelComic {
        return repositoryMarvelContract.findAllComicsByCharacter(
            characterID,
            1,
            app.getString(R.string.public_key),
            MarvelInfoUtils.createHash(app)
        )
    }

    override suspend fun findAllSeriesByCharacter(characterID: Int): ResponseMarvelComic {
        return repositoryMarvelContract.findAllSeriesByCharacter(
            characterID,
            1,
            app.getString(R.string.public_key),
            MarvelInfoUtils.createHash(app)
        )
    }
}