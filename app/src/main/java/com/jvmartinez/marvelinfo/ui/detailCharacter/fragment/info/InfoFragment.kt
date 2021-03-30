package com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.info

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.core.model.InfoGenericResult
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterViewModel
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterInfo
import com.jvmartinez.marvelinfo.ui.detailCharacter.dialog.Dialogs
import com.jvmartinez.marvelinfo.utils.MarvelTags
import kotlinx.android.synthetic.main.fragment_info_characte.*
import org.koin.android.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment(), InfoActions {
    private val detailsCharacterViewModel by viewModel<DetailsCharacterViewModel>()
    private var characterId: Int? = 0
    private var typeAction: Int? = 0
    private lateinit var adapterInfo: AdapterInfo

    override fun getLayoutId() = R.layout.fragment_info_characte

    override fun onSetup(view: View) {
        characterId?.let {
            typeAction?.let { actionType ->
                showLoading()
                detailsCharacterViewModel.findAllComics(it, actionType)
            }
        }
        onAdapter()
    }

    override fun onSetup() {
        arguments?.let {
            characterId = it.getInt(MarvelTags.CHARACTER_ID)
            typeAction = it.getInt(MarvelTags.TYPE_INFO_CHARACTER)
        }
    }

    private fun onAdapter() {
        adapterInfo = AdapterInfo(mutableListOf(), this)
        recyclerViewComics.layoutManager = LinearLayoutManager(context)
        recyclerViewComics.adapter = adapterInfo
    }

    override fun onObserver() {
        detailsCharacterViewModel.checkResponseComic.observe(::getLifecycle, ::flowComics)
        detailsCharacterViewModel.checkResponseSeries.observe(::getLifecycle, ::flowSeries)
        detailsCharacterViewModel.checkError.observe(::getLifecycle, ::showErrorDetailSCharacter)
    }

    private fun flowSeries(responseMarvelComic: ResponseMarvelComic?) {
        responseMarvelComic?.data?.results?.let {
            adapterInfo.onDataSeries(it)
        }
        hideLoading()
    }

    private fun flowComics(responseMarvelComic: ResponseMarvelComic?) {
        responseMarvelComic?.data?.results?.let {
            adapterInfo.onData(it)
        }
        hideLoading()
    }

    private fun showErrorDetailSCharacter(baseEnum: BaseEnum?) {
        hideLoading()
        when (baseEnum) {
            BaseEnum.ERROR_API_KEY -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_api)
                )
            }
            BaseEnum.ERROR_HASH -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_hash)
                )
            }
            BaseEnum.ERROR_TIMESTAMP -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_timestamp)
                )
            }
            BaseEnum.ERROR_REFERER -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_referer)
                )
            }
            BaseEnum.ERROR_INVALID_HASH -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_invalid_hash)
                )
            }
            BaseEnum.ERROR_NOT_ALLOWED -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_not_allowed)
                )
            }
            BaseEnum.ERROR_FORBIDDEN -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_forbidden)
                )
            }
            BaseEnum.ERROR_GENERIC -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_generic)
                )
            }
            BaseEnum.ERROR_PARAMETER -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_paramenter)
                )
            }
        }
    }

    override fun onGoWeb(url: String) {
        showBrowser(url)
    }

    override fun showDialogInfoComic(infoGeneric: InfoGenericResult) {
        context?.let {
            view?.let { view ->
                Dialogs.dialogInfo(it, view, infoGeneric)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(characterId: Int, typeAction: Int) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MarvelTags.CHARACTER_ID, characterId)
                    putInt(MarvelTags.TYPE_INFO_CHARACTER, typeAction)
                }
            }
    }
}
