package com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.core.model.InfoGenericResult
import com.jvmartinez.marvelinfo.databinding.FragmentInfoCharacteBinding
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterViewModel
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterInfo
import com.jvmartinez.marvelinfo.ui.detailCharacter.dialog.Dialogs
import com.jvmartinez.marvelinfo.utils.MarvelInfoError
import com.jvmartinez.marvelinfo.utils.MarvelTags
import org.koin.android.viewmodel.ext.android.viewModel

class InfoFragment: BaseFragment(), InfoActions {
    private var _binding: FragmentInfoCharacteBinding? = null
    private val binding: FragmentInfoCharacteBinding
        get() = _binding!!
    private val detailsCharacterViewModel by viewModel<DetailsCharacterViewModel>()
    private var characterId: Int? = null
    private var typeAction: Int? = null
    private lateinit var adapterInfo: AdapterInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoCharacteBinding.inflate(inflater, container, false)
        arguments?.let {
            characterId = it.getInt(MarvelTags.CHARACTER_ID)
            typeAction = it.getInt(MarvelTags.TYPE_INFO_CHARACTER)
        }

        return binding.root
    }


    override fun onSetup() {
        binding.customLoading.loading.visibility = View.VISIBLE
        characterId?.let {
            typeAction?.let { actionType ->
                detailsCharacterViewModel.findAllComics(it, actionType)
                    .observe(::getLifecycle, ::flowComics)
            }
        }
        onAdapter()
    }

    private fun onAdapter() {
        adapterInfo = AdapterInfo(mutableListOf(), this)
        binding.recyclerViewComics.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewComics.adapter = adapterInfo
    }

    private fun flowComics(apiResource: ApiResource<ResponseMarvelComic>?) {
        binding.customLoading.loading.visibility = View.GONE
        when (apiResource) {
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
                apiResource.data.data.results.let {
                    adapterInfo.onData(it)
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =  null
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
