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
import com.jvmartinez.marvelinfo.core.model.InfoGenericResult

object Dialogs {

    fun dialogInfo(context: Context, view: View, infoGeneric: InfoGenericResult) {
        val builder = AlertDialog.Builder(context)
        val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.dialog_info_comic, view as ViewGroup?,
                false)
        val title: TextView = dialogView.findViewById(R.id.dialogTitleComic)
        val description: TextView = dialogView.findViewById(R.id.dialogDescriptionComic)
        val photoComic: ImageView = dialogView.findViewById(R.id.dialogPhotoComic)
        val url = "${infoGeneric.thumbnail.path}/portrait_uncanny.${infoGeneric.thumbnail.extension}"
        Glide.with(dialogView)
                .load(url)
                .placeholder(R.drawable.ic_marvel_studios_2016_logo)
                .error(R.drawable.ic_deadpool_logo_150_150)
                .into(photoComic)
        title.text = infoGeneric.title
        if  (infoGeneric.textObjects?.isNotEmpty() == true) {
            description.text = infoGeneric.textObjects[0].text
        }
        builder.setView(dialogView)
        builder.show()
    }
}