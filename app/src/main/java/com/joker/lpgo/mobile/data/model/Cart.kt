package com.joker.lpgo.mobile.data.model

import com.joker.lpgo.data.model.Product

data class Cart(
    val uuid: String,
    val product: String,
    var qty: Int,
    val price: Int,
)
