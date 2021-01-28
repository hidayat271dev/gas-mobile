package com.joker.lpgo.data.model

data class Order (
    val id: Int = 0,
    val uuid: String = "",
    val order_number: String = "",
    val total: String = "",
    val order_status: String = "",
    val user_id: String = "",
)