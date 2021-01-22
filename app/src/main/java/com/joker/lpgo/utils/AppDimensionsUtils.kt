package com.joker.lpgo.utils

import android.content.Context
import androidx.annotation.DimenRes

/*
 * Copyright BTS.id 2020 - All Rights Reserved
 *
 * Created by Hidayat 20/10/20 16.15
 * hidayat@bts.id
 */

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
