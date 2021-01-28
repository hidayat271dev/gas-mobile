package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.base.BaseResponse

data class RecommendedResponse(
    val data: List<Product>
) : BaseResponse()
