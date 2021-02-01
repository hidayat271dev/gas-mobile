package com.joker.lpgo.mobile.data.remote.response

import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.base.BaseResponse
import com.joker.lpgo.mobile.data.model.Address
import com.joker.lpgo.mobile.data.model.Order

data class AddressListResponse(
    val data: List<Address>
) : BaseResponse()

data class AddressDetailResponse(
        val data: Address
) : BaseResponse()
