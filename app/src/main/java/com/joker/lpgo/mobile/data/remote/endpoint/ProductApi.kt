package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.ProductDetailResponse
import com.joker.lpgo.mobile.data.remote.response.ProductResponse
import com.joker.lpgo.mobile.data.remote.response.RecommendedResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("api/v1/products")
    fun getProduct(
    ): Observable<Response<ProductResponse>>

    @GET("api/v1/products/{uuid}")
    fun getDetailProduct(
        @Path("uuid") uuid: String
    ): Observable<Response<ProductDetailResponse>>

}