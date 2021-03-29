package com.jvmartinez.marvelinfo.ui.detailCharacter.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.model.ComicResult

object Dialogs {

    fun dialogComic(context: Context, view: View, comic: ComicResult) {
        val builder = AlertDialog.Builder(context)
        val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.dialog_info_comic, view as ViewGroup?,
                false)
        val title: TextView = dialogView.findViewById(R.id.dialogTitleComic)
        val description: TextView = dialogView.findViewById(R.id.dialogDescriptionComic)
        val photoComic: ImageView = dialogView.findViewById(R.id.dialogPhotoComic)
        val url = "${comic.thumbnail.path}/portrait_uncanny.${comic.thumbnail.extension}"
        Glide.with(dialogView)
                .load(url)
                .placeholder(R.drawable.ic_marvel_studios_2016_logo)
                .error(R.drawable.ic_baseline_mood_bad_120)
                .into(photoComic)
        title.text = comic.title
        if  (comic.textObjects.isNotEmpty()) {
            description.text = comic.textObjects[0].text
        }
        builder.setView(dialogView)
        builder.show()
    }
}