package com.carvalho.monnosbroker.features.coin_details.ui

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.carvalho.monnosbroker.R
import com.carvalho.monnosbroker.core.domain.models.Coin

@BindingAdapter("bindCoinTextColor")
fun TextView.bindCoinTextColor(coin: Coin?) {
    coin?.let {
        this.setTextColor(Color.parseColor(coin.color))
    }
}
