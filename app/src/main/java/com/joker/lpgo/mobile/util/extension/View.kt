package com.joker.lpgo.mobile.util.extension

import android.annotation.SuppressLint
import android.view.View

internal fun View.setShow(show: Boolean) {
    if(show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

internal fun View.show(show: Boolean) {
    if(show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@SuppressLint("UseCompatLoadingForColorStateLists")
@Suppress("DEPRECATION")
internal fun View.setColorTint(color: Int) {
    this.backgroundTintList = resources.getColorStateList(color)
}