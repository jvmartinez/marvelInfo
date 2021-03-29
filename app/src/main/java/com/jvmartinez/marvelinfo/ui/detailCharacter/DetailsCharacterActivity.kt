package com.jvmartinez.marvelinfo.ui.detailCharacter

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterCharacter
import com.jvmartinez.marvelinfo.utils.MarvelTags
import kotlinx.android.synthetic.main.content_detail_character.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsCharacterActivity : BaseActivity() {
    private val detailsCharacterViewModel by viewModel<DetailsCharacterViewModel>()
    private lateinit var adapterCharacter: AdapterCharacter
    private lateinit var extra: Bundle
    override fun layoutId() = R.layout.activity_details_character

    override fun onSetup() {
        showLoading()
        extra = intent.extras!!
        detailsCharacterViewModel.findCharacterById(extra.getInt(MarvelTags.CHARACTER_ID, 0))
        onAdapter()
        onCustomUI()
    }

    private fun onCustomUI() {
        TabLayoutMediator(itemsCharacter, contentCharacter) { tab, position ->
            contentCharacter.setCurrentItem(tab.position, true)
            tab.text =  when (position) {
                0 -> { getString(R.string.lblComics) }
                1 -> { getString(R.string.lblSeries) }
                2 -> { getString(R.string.lblEvents) }
               else ->  { getString(R.string.lblComics) }
           }
        }.attach()
    }

    private fun onAdapter() {
        adapterCharacter = AdapterCharacter(itemsCharacter.tabCount,
                extra.getInt(MarvelTags.CHARACTER_ID, 0),
                this)
        contentCharacter.adapter = adapterCharacter
    }

    override fun onObserver() {
        detailsCharacterViewModel.checkResponse.observe(::getLifecycle, ::showCharacter)
        detailsCharacterViewModel.checkError.observe(::getLifecycle, ::showErrorDetailSCharacter)
    }

    private fun showCharacter(responseMarvel: ResponseMarvel?) {
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
}