package com.jvmartinez.marvelinfo.ui.detailCharacter

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterCharacter
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import com.jvmartinez.marvelinfo.utils.MarvelTags
import kotlinx.android.synthetic.main.content_detail_character.*
import kotlinx.android.synthetic.main.custom_toolbar.*
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
        onClick()
    }

    private fun onClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onCustomUI() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayUseLogoEnabled(false)
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        TabLayoutMediator(itemsCharacter, contentCharacter) { tab, position ->
            contentCharacter.setCurrentItem(tab.position, true)
            showLoading()
            tab.text = when (position) {
                0 -> {
                    getString(R.string.lblComics)
                }
                1 -> {
                    getString(R.string.lblSeries)
                }
                else -> {
                    getString(R.string.lblComics)
                }
            }
        }.attach()
    }

    private fun onAdapter() {
        adapterCharacter = AdapterCharacter(
            itemsCharacter.tabCount,
            extra.getInt(MarvelTags.CHARACTER_ID, 0),
            this
        )
        contentCharacter.adapter = adapterCharacter
    }

    override fun onObserver() {
        detailsCharacterViewModel.checkResponse.observe(::getLifecycle, ::showCharacter)
        detailsCharacterViewModel.checkError.observe(::getLifecycle, ::showErrorDetailSCharacter)
    }

    private fun showCharacter(responseMarvel: ResponseMarvel?) {
        var url = ""

        responseMarvel?.let {
            toolbar.title = it.data.results[0].name
           url = "${it.data.results[0].thumbnail.path}/portrait_incredible.${it.data.results[0].thumbnail.extension}"
            if (it.data.results[0].description.isNotEmpty()) {
                infoCharacter.text = it.data.results[0].description
            } else {
                infoCharacter.text = getString(R.string.message_not_description)
            }
        }
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_marvel_studios_2016_logo)
            .error(R.drawable.ic_deadpool_logo_150_150)
            .into(infoPhotoCharacter)
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

    override fun onBackPressed() {
        super.onBackPressed()
        MarvelInfoUtils.callActivity(this, HomeActivity::class.java)
    }
}