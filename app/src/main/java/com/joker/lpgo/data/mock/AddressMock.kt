package com.joker.lpgo.data.mock

import com.joker.lpgo.data.model.Address


data class AddressListMock(
        val data: MutableList<Address> = mutableListOf<Address>(
                Address(0, true, "Address 1", "Address Street"),
                Address(0, false, "Address 1", "Address Street"),
                Address(0, false, "Address 1", "Address Street"),
        )
)