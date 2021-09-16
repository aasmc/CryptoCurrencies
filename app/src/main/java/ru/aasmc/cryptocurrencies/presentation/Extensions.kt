package ru.aasmc.cryptocurrencies.presentation

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.aasmc.cryptocurrencies.R

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_baseline_error_48)
        .into(this)
}