package com.joker.lpgo.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/*
 * Copyright BTS.id 2020 - All Rights Reserved
 *
 * Created by Hidayat 19/10/20 19.09
 * hidayat@bts.id
 */

internal fun View.visible() {
    this.visibility = View.VISIBLE
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal fun View.invisible() {
    this.visibility = View.INVISIBLE
}

internal fun View.show(show: Boolean) {
    if(show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(windowToken, InputMethodManager.SHOW_FORCED, 0)
}

@SuppressLint("UseCompatLoadingForColorStateLists")
@Suppress("DEPRECATION")
internal fun View.setColorTint(color: Int) {
    this.backgroundTintList = resources.getColorStateList(color)
}