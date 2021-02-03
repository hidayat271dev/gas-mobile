package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.NearbyResponse
import com.joker.lpgo.mobile.data.remote.response.OrderResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CheckoutApi {

    @FormUrlEncoded
    @POST("api/v1/orders")
    fun postOrder(
        @Field("item[]") items: MutableList<String>,
        @Field("qty[]") qyts: MutableList<Int>,
        @Field("order_status") status: Int,
        @Field("total") total: Int,
        @Field("address_id") address_id: String,
    ): Observable<Response<OrderResponse>>

}