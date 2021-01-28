package com.joker.lpgo.mobile.data.model

data class Order(
    val created_at: String,
    val order_number: String,
    val order_status: String,
    val total: String,
    val updated_at: String,
    val user_id: String,
    val uuid: String
)