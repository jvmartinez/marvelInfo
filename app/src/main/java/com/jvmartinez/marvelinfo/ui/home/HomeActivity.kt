package com.jvmartinez.marvelinfo.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.home.adapter.AdapterCharacters
import kotlinx.android.synthetic.main.content_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), HomeActions {
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapterCharacters: AdapterCharacters

    override fun layoutId() = R.layout.activity_home

    override fun onSetup() {
        homeViewModel.findCharacters()
        showLoading()
        onAdapter()
    }

    override fun onObserver() {
        homeViewModel.checkResponse.observe(::getLifecycle, ::showCharacters)
        homeViewModel.checkError.observe(::getLifecycle, ::showErrorHome)
    }

    private fun showErrorHome(homeEnum: HomeEnum?) {
        hideLoading()
        when (homeEnum) {
            HomeEnum.ERROR_API_KEY -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_api))
            }
            HomeEnum.ERROR_HASH -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_hash))
            }
            HomeEnum.ERROR_TIMESTAMP -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_timestamp))
            }
            HomeEnum.ERROR_REFERER -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_referer))
            }
            HomeEnum.ERROR_INVALID_HASH -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_invalid_hash))
            }
            HomeEnum.ERROR_NOT_ALLOWED -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_not_allowed))
            }
            HomeEnum.ERROR_FORBIDDEN -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_forbidden))
            }
            HomeEnum.ERROR_GENERIC -> {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_generic))
            }
        }
    }

    private fun showCharacters(responseMarvel: ResponseMarvel?) {
        hideLoading()
        if (::adapterCharacters.isInitialized) {
            adapterCharacters.onData(responseMarvel?.data?.results)
        }
    }

    private fun onAdapter() {
        adapterCharacters = AdapterCharacters(mutableListOf())
        recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        recyclerViewCharacters.adapter = adapterCharacters
    }

    override fun onShowCharacter(characterID: Int) {

    }
}