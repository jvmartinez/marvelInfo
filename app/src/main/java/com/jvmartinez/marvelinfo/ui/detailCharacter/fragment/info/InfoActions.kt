package com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.info

import com.jvmartinez.marvelinfo.core.model.InfoGenericResult

interface InfoActions {
     fun onGoWeb(url: String)

     fun showDialogInfoComic(infoGeneric: InfoGenericResult)
}