package com.joker.lpgo.utils.extension

import android.view.Window

/*
 * Copyright BTS.id 2020 - All Rights Reserved
 *
 * Created by Hidayat 21/10/20 18.38
 * hidayat@bts.id
 */

fun Window.getSoftInputMode() : Int {
    return attributes.softInputMode
}
