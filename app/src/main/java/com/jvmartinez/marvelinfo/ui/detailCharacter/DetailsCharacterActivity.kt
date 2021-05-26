package com.jvmartinez.marvelinfo.ui.detailCharacter

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterCharacter
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
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
        onAdapter()
        onCustomUI()
        onClick()
        onObserver()
    }

    private fun onObserver() {
        detailsCharacterViewModel.findCharacterById(extra.getInt(MarvelTags.CHARACTER_ID, 0))
            .observe(
                ::getLifecycle,
                ::showCharacter
            )
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

    fun showCharacter(apiResource: ApiResource<ResponseMarvel>?) {
        when (apiResource) {
            is ApiResource.Failure -> {
                when (MarvelInfoError.showError(apiResource.exception?.message.toString())) {
                    MarvelInfoError.ERROR_API_KEY -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_api)
                        )
                    }
                    MarvelInfoError.ERROR_HASH -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_hash)
                        )
                    }
                    MarvelInfoError.ERROR_TIMESTAMP -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_timestamp)
                        )
                    }
                    MarvelInfoError.ERROR_REFERER -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_referer)
                        )
                    }
                    MarvelInfoError.ERROR_INVALID_HASH -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_invalid_hash)
                        )
                    }
                    MarvelInfoError.ERROR_NOT_ALLOWED -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_not_allowed)
                        )
                    }
                    MarvelInfoError.ERROR_FORBIDDEN -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_forbidden)
                        )
                    }
                    MarvelInfoError.ERROR_GENERIC -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_generic)
                        )
                    }
                    MarvelInfoError.ERROR_PARAMETER_ID -> {
                        showMessage(
                            getString(R.string.title_notification),
                            getString(R.string.message_error_paramenter)
                        )
                    }
                }
            }
            is ApiResource.Success -> {
                var url = ""
                apiResource.let { resp ->
                    toolbar.title = resp.data.data.results[0].name
                    url =
                        "${resp.data.data.results[0].thumbnail.path}/portrait_incredible.${resp.data.data.results[0].thumbnail.extension}"
                    if (resp.data.data.results[0].description.isNotEmpty()) {
                        infoCharacter.text = resp.data.data.results[0].description
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
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MarvelInfoUtils.callActivity(this, HomeActivity::class.java)
    }
}
