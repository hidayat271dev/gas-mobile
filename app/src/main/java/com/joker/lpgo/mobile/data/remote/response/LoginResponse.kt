package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.mobile.base.BaseResponse
import com.joker.lpgo.mobile.data.model.Address
import com.joker.lpgo.mobile.data.model.User

data class LoginResponse(
    val data: LoginData?
) : BaseResponse()

data class LoginData(
    val token: String,
    val user: User,
    val current_address: Address
)