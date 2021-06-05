package com.jvmartinez.marvelinfo.core.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jvmartinez.marvelinfo.R

fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_marvel_studios_2016_logo)
        .error(R.drawable.ic_deadpool_logo_150_150)
        .into(this)
}