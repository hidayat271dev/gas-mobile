package com.joker.lpgo.mobile.base

import com.joker.lpgo.mobile.data.remote.response.ErrorResponse

open class BaseResponse {
    var message: String? = null
    var error: ErrorResponse? = null
}