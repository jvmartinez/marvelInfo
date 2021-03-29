package com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.comic

import com.jvmartinez.marvelinfo.core.model.ComicResult

interface ComicActions {
     fun onGoWeb(url: String)

     fun showDialogInfoComic(comic: ComicResult)
}