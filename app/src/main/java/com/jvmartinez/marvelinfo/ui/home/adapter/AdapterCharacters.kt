package com.jvmartinez.marvelinfo.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.model.Result
import kotlinx.android.synthetic.main.item_character.view.*

class AdapterCharacters(private var characters: List<Result>) :
        RecyclerView.Adapter<AdapterCharacters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    fun onData(characters: List<Result>?) {
        if (characters != null) {
            this.characters = characters
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(characters[position])
    }

    override fun getItemCount(): Int {
        return if (characters.isNotEmpty()) {
            characters.size
        } else {
            0
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindView(result: Result) {
            val url = "${result.thumbnail.path}/portrait_uncanny.${result.thumbnail.extension}"
            val transformation = MultiTransformation(CenterCrop(), RoundedCorners(
                    itemView.resources.getDimension(R.dimen.standard_rounder).toInt())
            )
            Glide.with(itemView)
                    .load(url)
                    .placeholder(R.drawable.ic_marvel_studios_2016_logo)
                    .error(R.drawable.ic_baseline_mood_bad_120)
                    .transform(transformation)
                    .into(itemView.photoCharacter)
            itemView.nameCharacter.text = result.name
            if (result.description.isNotEmpty()) {
                itemView.descriptionCharacter.text = result.description
            } else {
                itemView.descriptionCharacter.text = itemView.resources.getString(R.string.message_not_description)
            }
            itemView.modifiedCharacter.text = result.modified
        }
    }
}