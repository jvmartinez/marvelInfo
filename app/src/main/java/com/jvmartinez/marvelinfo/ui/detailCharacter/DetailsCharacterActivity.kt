package com.jvmartinez.marvelinfo.ui.detailCharacter

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.core.extension.load
import com.jvmartinez.marvelinfo.databinding.ActivityDetailsCharacterBinding
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterCharacter
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelTags
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsCharacterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsCharacterBinding
    private val detailsCharacterViewModel by viewModel<DetailsCharacterViewModel>()
    private lateinit var adapterCharacter: AdapterCharacter
    private var extra: Bundle? = null

    override fun layoutId(): ViewBinding {
        binding = ActivityDetailsCharacterBinding.inflate(layoutInflater)
        return binding
    }

    override fun onSetup() {
        binding.customLoading.loading.isVisible = true
        intent.extras.let {
            extra = it
        }
        onAdapter()
        onCustomUI()
        onClick()
        onObserver()
    }

    private fun onObserver() {
        extra.let {
            detailsCharacterViewModel.findCharacterById(it?.getInt(MarvelTags.CHARACTER_ID, 0)!!)
                .observe(
                    ::getLifecycle,
                    ::showCharacter
                )
        }
    }
    private fun onClick() {
        binding.customToolbar.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onCustomUI() {
        setSupportActionBar(binding.customToolbar.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayUseLogoEnabled(false)
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        TabLayoutMediator(
            binding.customDetails.itemsCharacter,
            binding.customDetails.contentCharacter
        ) { tab, position ->
            binding.customDetails.contentCharacter.setCurrentItem(tab.position, true)
            binding.customLoading.loading.isVisible = true
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
        extra.let {
            adapterCharacter = AdapterCharacter(
                binding.customDetails.itemsCharacter.tabCount,
                it?.getInt(MarvelTags.CHARACTER_ID, 0)!!,
                this
            )
        }

        binding.customDetails.contentCharacter.adapter = adapterCharacter
    }

    fun showCharacter(apiResource: ApiResource<ResponseMarvel>?) {
        binding.customLoading.loading.isVisible = false
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
                var url: String?
                apiResource.let { resp ->
                    binding.customToolbar.toolbar.title = resp.data.data.results[0].name
                    url =
                        "${resp.data.data.results[0].thumbnail.path}/portrait_incredible.${resp.data.data.results[0].thumbnail.extension}"
                    if (resp.data.data.results[0].description.isNotEmpty()) {
                        binding.customDetails.infoCharacter.text =
                            resp.data.data.results[0].description
                    } else {
                        binding.customDetails.infoCharacter.text =
                            getString(R.string.message_not_description)
                    }
                }
                binding.customDetails.infoPhotoCharacter.load(url)

            }
        }
    }

}
