package com.carvalho.monnosbroker.core.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bindImage")
fun ImageView.bindImage(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}