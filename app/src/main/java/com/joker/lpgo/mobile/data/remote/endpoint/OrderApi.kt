package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.OrderDetailResponse
import com.joker.lpgo.mobile.data.remote.response.OrderListResponse
import com.joker.lpgo.mobile.data.remote.response.OrderResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {

    @GET("api/v1/orders")
    fun getOrder(): Observable<Response<OrderListResponse>>

    @GET("api/v1/orders/{uuid}")
    fun getOrder(
        @Path("uuid") uuid: String
    ): Observable<Response<OrderDetailResponse>>

}