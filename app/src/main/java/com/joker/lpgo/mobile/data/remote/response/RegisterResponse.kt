package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.mobile.base.BaseResponse
import com.joker.lpgo.mobile.data.model.User

data class RegisterResponse(
    val data: User?
) : BaseResponse()
