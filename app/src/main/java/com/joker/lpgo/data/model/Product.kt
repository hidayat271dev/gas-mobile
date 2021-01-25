package com.joker.lpgo.data.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val short_desc: String = "",
    val desc: String = "",
    val prices: Int = 0,
    val stock: Int = 0,
    val images: List<String> = mutableListOf(),
)
