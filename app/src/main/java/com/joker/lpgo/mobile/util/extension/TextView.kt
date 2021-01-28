package com.joker.lpgo.mobile.util.extension

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

internal fun TextView.setColorTint(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}