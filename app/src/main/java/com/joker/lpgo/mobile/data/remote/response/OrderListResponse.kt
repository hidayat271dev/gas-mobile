package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.base.BaseResponse
import com.joker.lpgo.mobile.data.model.Order

data class OrderListResponse(
    val data: List<Order>
) : BaseResponse()
