package com.jvmartinez.marvelinfo.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.extension.load
import com.jvmartinez.marvelinfo.core.model.Result
import com.jvmartinez.marvelinfo.databinding.ItemCharacterBinding
import com.jvmartinez.marvelinfo.ui.home.HomeActions

class AdapterCharacters(
    private var characters: MutableList<Result>,
    private val homeActions: HomeActions
) :
    RecyclerView.Adapter<AdapterCharacters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    fun onData(characters: List<Result>?, search: Boolean) {

        characters.let { list ->
            list?.let {
                if (!search) {
                    this.characters.addAll(it)
                    notifyDataSetChanged()
                } else {
                    this.characters.clear()
                    this.characters.addAll(it)
                    notifyDataSetChanged()
                }

            }
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

    fun onDataSearch(results: List<Result>) {
        this.characters.clear()
        this.characters.addAll(results)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCharacterBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun onBindView(result: Result) {
            val url = "${result.thumbnail.path}/portrait_uncanny.${result.thumbnail.extension}"
            val transformation = MultiTransformation(
                CenterCrop(), RoundedCorners(
                    itemView.resources.getDimension(R.dimen.standard_rounder).toInt()
                )
            )
            binding.photoCharacter.load(url)
            binding.nameCharacter.text = result.name
            if (result.description.isNotEmpty()) {
                binding.descriptionCharacter.text = result.description
            } else {
                binding.descriptionCharacter.text =
                    itemView.resources.getString(R.string.message_not_description)
            }
            binding.goCharacter.text = itemView.context.getString(R.string.lblGoCharacter)

            itemView.setOnClickListener {
                homeActions.onShowCharacter(result.id)
            }
        }
    }
}