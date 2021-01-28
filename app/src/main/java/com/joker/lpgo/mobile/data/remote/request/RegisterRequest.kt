package com.joker.lpgo.mobile.data.remote.request

data class RegisterRequest(
    var fullname: String  = "",
    var email: String = "",
    var phone: String = "",
    var password: String = ""
)
