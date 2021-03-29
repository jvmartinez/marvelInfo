package com.jvmartinez.marvelinfo.ui.detailCharacter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.model.ComicResult
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.comic.ComicActions
import kotlinx.android.synthetic.main.item_comics_character.view.*

class AdapterComics(private var comics: List<ComicResult>,
                    private var comicActions: ComicActions) :
        RecyclerView.Adapter<AdapterComics.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterComics.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_comics_character, parent, false)
        return ViewHolder(view)
    }

    fun onData(comics: List<ComicResult>) {
        this.comics = comics
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AdapterComics.ViewHolder, position: Int) {
        holder.onBindView(comics[position])
    }

    override fun getItemCount(): Int {
        return if (comics.isEmpty()) {
            0
        } else {
            comics.size
        }
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        fun onBindView(comicResult: ComicResult) {
            val url = "${comicResult.thumbnail.path}/portrait_uncanny.${comicResult.thumbnail.extension}"
            val transformation = MultiTransformation(CenterCrop(), RoundedCorners(
                    itemView.resources.getDimension(R.dimen.standard_rounder).toInt())
            )
            Glide.with(itemView)
                    .load(url)
                    .placeholder(R.drawable.ic_marvel_studios_2016_logo)
                    .error(R.drawable.ic_baseline_mood_bad_120)
                    .transform(transformation)
                    .into(itemView.photoComic)
            itemView.titleComic.text = comicResult.title
            if (comicResult.description?.isNotEmpty() == true) {
                itemView.descriptionComic.text = comicResult.description
            } else {
                itemView.descriptionComic.text = itemView.resources.getString(R.string.message_not_description)
            }
            itemView.btnGoWeb.setOnClickListener {
                comicResult.urls.forEach {
                    if (it.type == "detail") {
                        comicActions.onGoWeb(it.url)
                    }
                }
            }
            itemView.setOnClickListener {
                comicActions.showDialogInfoComic(comicResult)
            }
        }
    }
}