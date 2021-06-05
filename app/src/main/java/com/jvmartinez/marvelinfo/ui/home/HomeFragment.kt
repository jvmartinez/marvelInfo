package com.jvmartinez.marvelinfo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvel
import com.jvmartinez.marvelinfo.databinding.FragmentHomeBinding
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import com.jvmartinez.marvelinfo.ui.home.adapter.AdapterCharacters
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelTags
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), HomeActions, SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
    get() = _binding!!
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapterCharacters: AdapterCharacters
    private var offset: Int = 0
    private var searchStatus = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetup() {
        homeViewModel.findCharacters(offset).observe(::getLifecycle, ::showCharacters)
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
                    offset += 50
                    binding.customLoading.loading.visibility = View.VISIBLE
                    homeViewModel.findCharacters(offset).observe(::getLifecycle, ::showCharacters)
                }
            }
        })

        binding.customHome.searchCharacter.setOnCloseListener(object : android.widget.SearchView.OnCloseListener,
            SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.customLoading.loading.visibility = View.VISIBLE
                searchStatus = false
                homeViewModel.findCharacters(0).observe(::getLifecycle, ::showCharacters)
                return false
            }

        })
    }

    private fun showCharacters(apiResource: ApiResource<ResponseMarvel>?) {
        binding.customLoading.loading.visibility = View.GONE
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
        binding.customHome.recyclerViewCharacters.layoutManager = LinearLayoutManager(context)
        binding.customHome.recyclerViewCharacters.setHasFixedSize(true)
        binding.customHome.recyclerViewCharacters.adapter = adapterCharacters
    }

    override fun onShowCharacter(characterID: Int) {
        val bundle = Bundle()
        bundle.putInt(MarvelTags.CHARACTER_ID, characterID)
        findNavController().navigate(R.id.action_homeFragment_to_detailsCharacterActivity, bundle)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchStatus = true
        homeViewModel.findCharacters(0, query).observe(::getLifecycle, ::showCharacters)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}