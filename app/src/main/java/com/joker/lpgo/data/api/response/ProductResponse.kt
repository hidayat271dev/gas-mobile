package com.joker.lpgo.data.api.response

import com.joker.lpgo.data.model.Product

data class ProductListResponse(
    val data: List<Product>
) : BaseResponse()
