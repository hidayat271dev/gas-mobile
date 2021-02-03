package com.joker.lpgo.mobile.data.model

data class Address(
    val id: Int,
    val is_primary: String,
    val lat: Double,
    val long: Double,
    val name: String,
    val user_id: String,
    val uuid: String,
    val address_meta: String,
)