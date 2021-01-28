package com.joker.lpgo.mobile.util

import android.content.Context
import androidx.annotation.DimenRes

class AppDimensionsUtils private constructor() {

    init {
        throw InstantiationException("This utility class is created for instantiation")
    }

    companion object {
        private var scale = 0f

        fun getDimension(context: Context, @DimenRes resourceId: Int): Float {
            return context.resources.getDimension(resourceId)
        }

        fun getDimensionPixelSize(context: Context, @DimenRes resourceId: Int): Int {
            return context.resources.getDimensionPixelSize(resourceId)
        }

        fun dpToPixel(dp: Float, context: Context): Int {
            if (scale == 0f) {
                scale = context.resources.displayMetrics.density
            }
            return (dp * scale).toInt()
        }

        fun pixelToDp(pixel: Int, context: Context): Int {
            if (scale == 0f) {
                scale = context.resources.displayMetrics.density
            }
            return (pixel * scale).toInt()
        }
    }
}
