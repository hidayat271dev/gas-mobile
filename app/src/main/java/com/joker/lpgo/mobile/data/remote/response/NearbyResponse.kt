package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.base.BaseResponse

data class NearbyResponse(
    val data: List<Product>
) : BaseResponse()
