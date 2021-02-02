package com.joker.lpgo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int = 0,
    val uuid: String = "",
    val name: String = "",
    val qty: Int = 0,
    val short_desc: String = "",
    val desc: String = "",
    val status_product: Int = 0,
    val sale_price: Int = 0,
    val img_cover: String = "",
    val imaimg_galleryges: List<String> = mutableListOf(),
): Parcelable
