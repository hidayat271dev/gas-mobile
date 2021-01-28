package com.joker.lpgo.mobile.util.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.joker.lpgo.mobile.R

internal fun ImageView.setImageWithColor(picture: Int, color: Int) {
    this.setImageResource(picture)
    this.setColorFilter(ContextCompat.getColor(context, color))
}

fun ImageView.load(url: String?) {
    Glide.with(this).load(url).error(R.drawable.ic_no_image_default).into(this)
}