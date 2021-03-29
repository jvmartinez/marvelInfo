package com.jvmartinez.marvelinfo.ui.home

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterActivity
import com.jvmartinez.marvelinfo.ui.home.adapter.AdapterCharacters
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import com.jvmartinez.marvelinfo.utils.MarvelTags
import kotlinx.android.synthetic.main.content_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), HomeActions, SearchView.OnQueryTextListener {
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapterCharacters: AdapterCharacters
    private var offset: Int = 0

    override fun layoutId() = R.layout.activity_home

    override fun onSetup() {
        homeViewModel.findCharacters(offset)
        showLoading()
        onAdapter()
        onClick()
        customUI()
    }

    private fun customUI() {
        searchCharacter.setOnQueryTextListener(this)
    }

    private fun onClick() {
        recyclerViewCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    showLoading()
                    offset += 50
                    homeViewModel.findCharacters(offset)
                }
            }
        })
    }

    override fun onObserver() {
        homeViewModel.checkResponse.observe(::getLifecycle, ::showCharacters)
        homeViewModel.checkError.observe(::getLifecycle, ::showErrorHome)
    }

    private fun showErrorHome(homeEnum: HomeEnum?) {
        hideLoading()
        when (homeEnum) {
            HomeEnum.ERROR_API_KEY -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_api)
                )
            }
            HomeEnum.ERROR_HASH -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_hash)
                )
            }
            HomeEnum.ERROR_TIMESTAMP -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_timestamp)
                )
            }
            HomeEnum.ERROR_REFERER -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_referer)
                )
            }
            HomeEnum.ERROR_INVALID_HASH -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_invalid_hash)
                )
            }
            HomeEnum.ERROR_NOT_ALLOWED -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_not_allowed)
                )
            }
            HomeEnum.ERROR_FORBIDDEN -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_forbidden)
                )
            }
            HomeEnum.ERROR_GENERIC -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_generic)
                )
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
        adapterCharacters = AdapterCharacters(mutableListOf(), this)
        recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        recyclerViewCharacters.setHasFixedSize(true)
        recyclerViewCharacters.adapter = adapterCharacters
    }

    override fun onShowCharacter(characterID: Int) {
        val bundle = Bundle()
        bundle.putInt(MarvelTags.CHARACTER_ID, characterID)
        MarvelInfoUtils.callActivity(
            this,
            DetailsCharacterActivity::class.java,
            bundle
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}