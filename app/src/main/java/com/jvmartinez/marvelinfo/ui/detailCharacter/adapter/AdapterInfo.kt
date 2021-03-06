package com.jvmartinez.marvelinfo.ui.detailCharacter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.extension.load
import com.jvmartinez.marvelinfo.core.model.InfoGenericResult
import com.jvmartinez.marvelinfo.databinding.ItemComicsCharacterBinding
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.info.InfoActions

class AdapterInfo(private var data: MutableList<InfoGenericResult>,
                  private var infoActions: InfoActions) :
        RecyclerView.Adapter<AdapterInfo.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInfo.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_comics_character, parent, false)
        return ViewHolder(view)
    }

    fun onData(infoGenerics: List<InfoGenericResult>) {
        this.data.clear()
        this.data.addAll(infoGenerics)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AdapterInfo.ViewHolder, position: Int) {
        holder.onBindView(data[position])
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) {
            0
        } else {
            data.size
        }
    }

    fun onDataSeries(series: List<InfoGenericResult>) {
        this.data.clear()
        this.data.addAll(series)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
         val binding = ItemComicsCharacterBinding.bind(itemView)

        fun onBindView(infoGenericResult: InfoGenericResult) {
            val url = "${infoGenericResult.thumbnail.path}/portrait_uncanny.${infoGenericResult.thumbnail.extension}"
            val transformation = MultiTransformation(CenterCrop(), RoundedCorners(
                    itemView.resources.getDimension(R.dimen.standard_rounder).toInt())
            )
            binding.photoComic.load(url)
            binding.titleComic.text = infoGenericResult.title
            if (infoGenericResult.description?.isNotEmpty() == true) {
                binding.descriptionComic.text = infoGenericResult.description
            } else {
                binding.descriptionComic.text = itemView.resources.getString(R.string.message_not_description)
            }
            binding.btnGoWeb.setOnClickListener {
                infoGenericResult.urls.forEach {
                    if (it.type == "detail") {
                        infoActions.onGoWeb(it.url)
                    }
                }
            }
            itemView.setOnClickListener {
                infoActions.showDialogInfoComic(infoGenericResult)
            }
        }
    }
}