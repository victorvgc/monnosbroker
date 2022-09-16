package com.carvalho.monnosbroker.core.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.carvalho.monnosbroker.R

@BindingAdapter("bindImage")
fun ImageView.bindImage(url: String?) {
    url?.let {
        Glide
            .with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("bindIndexPercent")
fun TextView.bindIndexPercent(percent: Float) {
    val color: Int = if (percent >= 0) {
        R.color.positive_text
    } else {
        R.color.negative_text
    }

    this.setTextColor(this.context.getColor(color))
    this.text = "$percent%"
}

@BindingAdapter("bindFavorite")
fun ImageView.bindFavorite(isFav: Boolean) {
    val id = if (isFav) {
        R.drawable.ic_fav_selected
    } else {
        R.drawable.ic_fav_unselected
    }

    Glide
        .with(this.context)
        .load(id)
        .centerCrop()
        .into(this)
}