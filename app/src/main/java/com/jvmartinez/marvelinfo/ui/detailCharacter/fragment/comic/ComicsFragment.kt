package com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.comic

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ResponseMarvelComic
import com.jvmartinez.marvelinfo.core.model.ComicResult
import com.jvmartinez.marvelinfo.ui.base.BaseEnum
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterViewModel
import com.jvmartinez.marvelinfo.ui.detailCharacter.adapter.AdapterComics
import com.jvmartinez.marvelinfo.ui.detailCharacter.dialog.Dialogs
import com.jvmartinez.marvelinfo.utils.MarvelTags
import kotlinx.android.synthetic.main.fragment_comics.*
import org.koin.android.viewmodel.ext.android.viewModel

class ComicsFragment : BaseFragment(), ComicActions {
    private val detailsCharacterViewModel by viewModel<DetailsCharacterViewModel>()
    private var characterId: Int? = 0
    private lateinit var adapterComics: AdapterComics

    override fun getLayoutId() = R.layout.fragment_comics

    override fun onSetup(view: View) {
        characterId?.let {
            detailsCharacterViewModel.findAllComics(it)
        }
        onAdapter()
    }

    override fun onSetup() {
        arguments?.let {
            characterId = it.getInt(MarvelTags.CHARACTER_ID)
        }
    }

    private fun onAdapter() {
        adapterComics = AdapterComics(mutableListOf(), this)
        recyclerViewComics.layoutManager = LinearLayoutManager(context)
        recyclerViewComics.adapter = adapterComics
    }

    override fun onObserver() {
        detailsCharacterViewModel.checkResponseComic.observe(::getLifecycle, ::flowComics)
        detailsCharacterViewModel.checkError.observe(::getLifecycle, ::showErrorDetailSCharacter)
    }

    private fun flowComics(responseMarvelComic: ResponseMarvelComic?) {
        responseMarvelComic?.data?.results?.let {
            adapterComics.onData(it)
        }
    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_comics, container, false)
//    }
//

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

    }

    override fun showDialogInfoComic(comic: ComicResult) {
        context?.let {
            view?.let { view ->
                Dialogs.dialogComic(it, view, comic)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(characterId: Int) =
                ComicsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(MarvelTags.CHARACTER_ID, characterId)
                    }
                }
    }
}