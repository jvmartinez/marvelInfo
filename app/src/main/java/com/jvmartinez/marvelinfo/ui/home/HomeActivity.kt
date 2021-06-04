package com.jvmartinez.marvelinfo.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.databinding.ActivityHomeBinding
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterActivity
import com.jvmartinez.marvelinfo.ui.home.adapter.AdapterCharacters
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import com.jvmartinez.marvelinfo.utils.MarvelTags
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), HomeActions, SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapterCharacters: AdapterCharacters
    private var offset: Int = 0
    private var searchStatus = false

    override fun layoutId(): ViewBinding  {
      binding = ActivityHomeBinding.inflate(layoutInflater)
      return binding
    }

    override fun onSetup() {
        homeViewModel.findCharacters(offset).observe(::getLifecycle, ::showCharacters)
        showLoading()
        onAdapter()
        onClick()
        customUI()
    }

    private fun customUI() {
        binding.customHome.searchCharacter.setOnQueryTextListener(this)
    }

    private fun onClick() {
        binding.customHome.recyclerViewCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    searchStatus = false
                    showLoading()
                    offset += 50
                    homeViewModel.findCharacters(offset).observe(::getLifecycle, ::showCharacters)
                }
            }
        })

        binding.customHome.searchCharacter.setOnCloseListener(object : android.widget.SearchView.OnCloseListener,
            SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                showLoading()
                searchStatus = false
                homeViewModel.findCharacters(0).observe(::getLifecycle, ::showCharacters)
                return false
            }

        })
    }

    private fun showCharacters(apiResource: ApiResource<ResponseMarvel>?) {
        hideLoading()
        when(apiResource) {
            is ApiResource.Failure ->  {
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
                if (::adapterCharacters.isInitialized) {
                    binding.customHome.customError.root.visibility = View.GONE
                    apiResource.data.data.results.let {
                        adapterCharacters.onData(it,searchStatus)
                    }
                } else {
                    binding.customHome.customError.root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onAdapter() {
        adapterCharacters = AdapterCharacters(mutableListOf(), this)
        binding.customHome.recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        binding.customHome.recyclerViewCharacters.setHasFixedSize(true)
        binding.customHome.recyclerViewCharacters.adapter = adapterCharacters
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
        showLoading()
        searchStatus = true
        homeViewModel.findCharacters(0, query).observe(::getLifecycle, ::showCharacters)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}