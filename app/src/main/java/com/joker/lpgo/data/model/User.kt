package com.joker.lpgo.data.model

data class User(
    val id: Int = 0,
    val uuid: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val access: String = "",
    val account_status: String = "",
    val pic_profile: String = "",
    val pic_background: String = "",
)
