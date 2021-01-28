package com.joker.lpgo.mobile.util.extension

import android.view.Window

fun Window.getSoftInputMode() : Int {
    return attributes.softInputMode
}